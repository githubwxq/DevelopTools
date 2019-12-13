package com.wxq.commonlibrary.mydb.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.wxq.commonlibrary.mydb.annotation.DbField;
import com.wxq.commonlibrary.mydb.annotation.DbTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1.完成自动建表的功能
 */

public class BaseDao<T> implements IBaseDao<T> {
    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;
    //持有操作数据库所对应的java类型
    private Class<T> entityClass;
    //标识：用来表示是否做过初始化操作
    private boolean isInit = false;
    //定义一个缓存空间(key-字段名    value-成员变量)
    private HashMap<String, Field> cacheMap;


    //框架内部的逻辑，最好不要提供构造方法给调用层能用
    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        //可以根据传入的entityClass类型来建立表,只需要建一次
        if (!isInit) {
            //自动建表
            //取到表名
            if (entityClass.getAnnotation(DbTable.class) == null) {
                //反射到类名
                tableName = entityClass.getSimpleName();
            } else {
                //取注解上的名字
                tableName = entityClass.getAnnotation(DbTable.class).value();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //执行建表操作
            //create table if not exists tb_user(_id integer,name varchar(20),password varchar(20))
            //单独用个方法来生成create命令
            String createTableSql = getCreateTableSql();
            sqLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }

        return isInit;
    }

    private void initCacheMap() {
        //1.取到所有的列名
        String sql = "select * from " + tableName + " limit 1,0";//空表
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        //2.取所有的成员变量
        Field[] columnFields = entityClass.getDeclaredFields();
        //把所有字段的访问权限打开
        for (Field field : columnFields) {
            field.setAccessible(true);
        }
        //对1和2进行映射
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : columnFields) {
                String fieldName = null;
                if (field.getAnnotation(DbField.class) != null) {
                    fieldName = field.getAnnotation(DbField.class).value();
                } else {
                    fieldName = field.getName();
                }
                if (columnName.equals(fieldName)) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                cacheMap.put(columnName, columnField);
            }
        }
    }

    private String getCreateTableSql() {
        //create table if not exists tb_user(_id INTEGER,name TEXT,password TEXT)
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");
        //反射得到所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();//拿到成员的类型
            if (field.getAnnotation(DbField.class) != null) {
                if (type == String.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BLOB,");
                } else {
                    //不支持的类型号
                    continue;
                }
            } else {
                if (type == String.class) {
                    stringBuffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getName() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getName() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getName() + " BLOB,");
                } else {
                    //不支持的类型号
                    continue;
                }
            }

        }
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public long insert(T entity) {
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("_id",'1');//缓存   _id   id变量
//        contentValues.put("name","jett");
//        sqLiteDatabase.insert(tableName,null, contentValues);
        //准备好ContentValues中需要的数据
        Map<String, String> map = getValues(entity);
        //把数据转移到ContentValues中
        ContentValues values = getContentValues(map);
        //开始插入
        long result = sqLiteDatabase.insert(tableName, null, values);

        return result;
    }

    @Override
    public long update(T entity, T where) {
//        sqLiteDatabase.update(tableName,contentValues,"name=",new String[]{"jett"});
        int result = -1;
        Map values = getValues(entity);
        ContentValues contentValues = getContentValues(values);
        Map whereCause = getValues(where);//key==_id   value=1
        Condition condition = new Condition(whereCause);
        result = sqLiteDatabase.update(tableName, contentValues, condition.whereCasue, condition.whereArgs);
        return result;
    }

    private class Condition {
        private String whereCasue;//"name=? and password=?"
        private String[] whereArgs;//new String[]{"jett"}

        public Condition(Map<String, String> whereCasue) {
            ArrayList list = new ArrayList();//whereArgs里面的内容存入list
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1=1");
            //取所有的字段名
            Set keys = whereCasue.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = whereCasue.get(key);
                if (value != null) {
                    stringBuilder.append(" and " + key + "=?");
                    list.add(value);
                }
            }
            this.whereCasue = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);

        }
    }


    @Override
    public int delete(T where) {
//        sqLiteDatabase.delete(tableName,"name=?",new String[]{});
        Map map = getValues(where);
        Condition condition = new Condition(map);
        int result = sqLiteDatabase.delete(tableName, condition.whereCasue, condition.whereArgs);
        return result;
    }


    @Override
    public List<T> query(T where) {

        return query(where, null, null, null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
//        sqLiteDatabase.query(tableName,null,"id=?",new String[],null,null,orderBy,"1,5");
        Map map = getValues(where);
        String limitString = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        Condition condition = new Condition(map);

        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereCasue, condition.whereArgs, null, null, orderBy, limitString);
        //定义一个用来解析游标的方法
        List<T> result = getResult(cursor, where);
        return result;
    }

    //obj是用来表示User类的结构
    private List<T> getResult(Cursor cursor, T obj) {
        ArrayList list=new ArrayList();
        Object item=null;
        while(cursor.moveToNext()){
            try {
                item=obj.getClass().newInstance();//new User();
                Iterator iterator=cacheMap.entrySet().iterator();//字段-成员变量
                while(iterator.hasNext()){
                    Map.Entry entry=(Map.Entry)iterator.next();
                    //取列名
                    String columnName=(String)entry.getKey();
                    //然后以列名拿到列名在游标中的位置
                    Integer columnIndex=cursor.getColumnIndex(columnName);

                    Field field=(Field)entry.getValue();
                    Class type=field.getType();

                    if(columnIndex!=-1){
                        if(type==String.class){
                            field.set(item,cursor.getString(columnIndex));
                        }else if(type==Double.class){
                            field.set(item,cursor.getDouble(columnIndex));
                        }else if(type==Integer.class){
                            field.set(item,cursor.getInt(columnIndex));
                        }else if(type==Long.class){
                            field.set(item,cursor.getLong(columnIndex));
                        }else if(type==byte[].class){
                            field.set(item,cursor.getBlob(columnIndex));
                        }else{
                            continue;
                        }
                    }
                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }



    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    //字段--成员变量          getValues后   字段--值
    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        //返回的是所有的成员变量,user的成员变量
        Iterator<Field> fieldIterator = cacheMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            //获取成员变量的值
            try {
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }
                String value = object.toString();
                //获取列名
                String key = null;
                if (field.getAnnotation(DbField.class) != null) {
                    key = field.getAnnotation(DbField.class).value();
                } else {
                    key = field.getName();
                }
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}







