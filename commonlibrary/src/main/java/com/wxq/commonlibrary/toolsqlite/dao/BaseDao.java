package com.wxq.commonlibrary.toolsqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.wxq.commonlibrary.toolsqlite.annotation.DBField;
import com.wxq.commonlibrary.toolsqlite.annotation.DBTable;
import com.wxq.commonlibrary.toolsqlite.bean.DBInfo;
import com.wxq.commonlibrary.toolsqlite.util.Tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * 基础操作类
 * 能够自动建表
 *
 * @param <T>
 */
public class BaseDao<T> implements BaseDaoInterface<T> {
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
    //缓存字段名与变量名
    private HashMap<String, String> cacheMap2;
    //数据库升级失败
    private int UPGRADE_FAIL = -1;
    //数据库升级成功
    private int UPGRADE_SUCCESS = 0;

    /**
     * 初始化工作
     * 框架内部的逻辑，最好不要提供构造方法给调用层能用
     *
     * @param sqLiteDatabase 系统的数据库操作类
     * @param entityClass    实体类，传入Class方便操作
     * @return 初始化是否成功
     */
    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        //可以根据传入的entityClass类型来建立表,只需要建一次
        if (!isInit) {
            //自动建表
            //取到表名
            if (entityClass.getAnnotation(DBTable.class) == null) {
                //反射到类名
                tableName = entityClass.getSimpleName();
            } else {
                //取注解上的名字
                tableName = entityClass.getAnnotation(DBTable.class).value();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //执行建表操作
            String createTableSql = getCreateTableSql();
            sqLiteDatabase.execSQL(createTableSql);
            isInit = true;
        }
        return isInit;
    }

    /**
     * 生成自动建表语句
     * 并缓存字段名与成员变量,方便后续操作，减少反射次数
     *
     * @return
     */
    private String getCreateTableSql() {
        return getCreateTableSql(null);
    }

    /**
     * 生成自动建表语句
     * 并缓存字段名与成员变量,方便后续操作，减少反射次数
     *
     * @return
     */
    private String getCreateTableSql(Class myClass) {
        Class entry = myClass == null ? entityClass : myClass;
        cacheMap = new HashMap<>();

        //create table if not EXISTS  tb_user(_id INTEGER,name TEXT,password TEXT)
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not EXISTS  ");
        stringBuffer.append(tableName + "(");
        //反射得到所有的成员变量
        Field[] fields = entry.getDeclaredFields();
        for (Field field : fields) {
            //获取字段名
            String fieldName = field.getName();
            //使用注解信息设置字段名
            if (field.getAnnotation(DBField.class) != null) {
                fieldName = field.getAnnotation(DBField.class).value();
            }
            //拿到成员的类型
            Class type = field.getType();
            //根据成员的类型设置字段的类型
            if (type == String.class) {
                stringBuffer.append(fieldName + " TEXT,");
            } else if (type == Integer.class) {
                stringBuffer.append(fieldName + " INTEGER,");
            } else if (type == Long.class) {
                stringBuffer.append(fieldName + " BIGINT,");
            } else if (type == Double.class) {
                stringBuffer.append(fieldName + " DOUBLE,");
            } else if (type == byte[].class) {
                stringBuffer.append(fieldName + " BLOB,");
            } else {
                continue;
            }

            cacheMap.put(fieldName, field);
        }
        //去掉尾部的','
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /**
     * 添加数据
     *
     * @param entity
     * @return
     */
    @Override
    public long insert(T entity) {
//        原始写法
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", "1");//缓存   _id   id变量
//        contentValues.put("name", "jett");
//        sqLiteDatabase.insert(tableName, null, contentValues);

        //准备好ContentValues中需要的数据
        ContentValues values = getContentValues(entity);
        //开始插入
        long result = sqLiteDatabase.insert(tableName, null, values);
        return result;
    }

    /**
     * 批量添加数据
     *
     * @param list
     * @return
     */
    @Override
    public long insert(List<T> list) {
        if (Tool.isEmpty(list)) {
            return -1;
        }
        long result = 0;
        for (T t : list) {
            //准备好ContentValues中需要的数据
            ContentValues values = getContentValues(t);
            //开始插入
            result += sqLiteDatabase.insert(tableName, null, values);
        }
        return result;
    }

    /**
     * 批量添加数据
     *
     * @param list
     * @return
     */
    private long insertContentValues(List<ContentValues> list) {
        if (Tool.isEmpty(list)) {
            return -1;
        }
        long result = 0;
        for (ContentValues t : list) {
            //开始插入
            result += sqLiteDatabase.insert(tableName, null, t);
        }
        return result;
    }

    /**
     * 将实体对象转化为字段名与值
     *
     * @param entity
     * @return
     */
    private Map<String, String> getValues(T entity) {
        if (entity == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
            String fieldName = entry.getKey();
            Field field = entry.getValue();
            String fieldValue = null;
            try {
                field.setAccessible(true);
                Object o = field.get(entity);
                if (o != null) {
                    fieldValue = o.toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(fieldName) && !TextUtils.isEmpty(fieldValue)) {
                map.put(fieldName, fieldValue);
            }
        }
        return map;
    }

    /**
     * 将实体对象转化为ContentValues
     *
     * @param entity
     * @return
     */
    private ContentValues getContentValues(T entity) {
        if (entity == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
            String fieldName = entry.getKey();
            Field field = entry.getValue();
            String fieldValue = null;
            try {
                field.setAccessible(true);
                Object o = field.get(entity);
                if (o != null) {
                    fieldValue = o.toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(fieldName) && !TextUtils.isEmpty(fieldValue)) {
                contentValues.put(fieldName, fieldValue);
            }
        }
        return contentValues;
    }

    /**
     * 将字段名与值转化为ContentValues
     *
     * @param map
     * @return
     */
    private ContentValues getContentValues(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();
            if (fieldValue != null) {
                contentValues.put(fieldName, fieldValue);
            }
        }
        return contentValues;
    }

    /**
     * 更新数据
     *
     * @param entity 修改后的数据
     * @param where  查询条件
     * @return 修改记录数
     */
    @Override
    public long update(T entity, T where) {
        //sqLiteDatabase.update(tableName,contentValues,"name=",new String[]{"jett"});
        int result = -1;
        ContentValues contentValues = getContentValues(entity);
        Map whereCause = getValues(where);//key==_id   value=1
        Condition condition = new Condition(whereCause);
        result = sqLiteDatabase.update(tableName, contentValues, condition.whereCasue, condition.whereArgs);
        return result;
    }

    /**
     * 删除数据
     *
     * @param where 查询条件
     * @return 修改记录数
     */
    @Override
    public int delete(T where) {
        //sqLiteDatabase.delete(tableName,"name=?",new String[]{});
        Map map = getValues(where);
        Condition condition = new Condition(map);
        int result = sqLiteDatabase.delete(tableName, condition.whereCasue, condition.whereArgs);
        return result;
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @Override
    public List<T> query() {
        return query(null);
    }

    /**
     * 按条件查询数据
     *
     * @param where 查询条件
     * @return 查询结果
     */
    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }

    /**
     * 按条件查询数据
     *
     * @param where   查询条件
     * @param orderBy 排序方式
     * @param start   返回结果起始行
     * @param end     返回结果结束行（不包括）
     * @return 查询结果
     */
    @Override
    public List<T> query(T where, String orderBy, Integer start, Integer end) {
        //sqLiteDatabase.query(tableName, null, "id=?", new String[], null, null, orderBy, "1,5");

        Map map = getValues(where);
        String limitString = null;
        if (start != null && end != null) {
            limitString = start + " , " + end;
        }
        //封装成指定格式的查询条件
        Condition condition = new Condition(map);
        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereCasue, condition.whereArgs, null, null, orderBy, limitString);
        //定义一个用来解析游标的方法
        List<T> result = getResult(cursor, where);
        return result;
    }


    /**
     * 将游标结果转化为对象集合
     *
     * @param cursor 游标结果
     * @param bean   实体对象
     * @return 对象集合
     */
    private List<T> getResult(Cursor cursor, T bean) {
        List list = new ArrayList();
        Object item = null;
        while (cursor.moveToNext()) {
            try {
                if (bean == null) {
                    item = entityClass.newInstance();
                } else {
                    item = bean.getClass().newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
                String columnName = entry.getKey();
                Field field = entry.getValue();
                Class type = field.getType();
                Integer columnIndex = cursor.getColumnIndex(columnName);
                if (columnIndex != -1) {
                    try {
                        field.setAccessible(true);
                        if (type == String.class) {
                            field.set(item, cursor.getString(columnIndex));
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                        } else {
                            continue;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            list.add(item);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询条件
     */
    private class Condition {
        private String whereCasue;//"name=? and password=?"
        private String[] whereArgs;//new String[]{"jett"}

        public Condition(Map<String, String> whereCasue) {
            if (whereCasue == null) {
                return;
            }
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

    /**
     * 删除表
     *
     * @return
     */
    @Override
    public boolean deleteTable() {
        String sql = "DROP TABLE " + tableName;
        sqLiteDatabase.execSQL(sql);
        return true;
    }

    /**
     * 更新数据库
     * TODO 待优化:直接读取数据到内存会导致内存不足.优化方向:通过需要update的对象转化为sql语句.
     */
    @Override
    public <M> int upgrade(DBInfo dbInfo, Class<M> newTable) {
        if (entityClass == null || newTable == null || entityClass.getSimpleName().equals(newTable.getSimpleName())) {
            return UPGRADE_FAIL;
        }
        //查询旧表所有数据
        List<T> dataList = query();
        //删除旧表
        deleteTable();
        BaseDaoFactory baseDaoFactory = BaseDaoFactory.getInstance(dbInfo);
        //创建新表
        BaseDao<M> newDao = baseDaoFactory.getBaseDao(newTable);
        List<ContentValues> newDataList = getNewEntryList(dataList, newDao);
        newDao.insertContentValues(newDataList);
        //重启数据库，是为了清空游标缓存数据(如果不清空，那就会使用旧表的Cursor缓存)
        baseDaoFactory.restartDB();
        return UPGRADE_SUCCESS;
    }


    /**
     * 获取新表数据
     * 如果旧表的字段名或变量名等于新表的字段名或变量名，则把这个数据插入新表
     *
     * @param dataList
     * @return
     */
    private List<ContentValues> getNewEntryList(List<T> dataList, BaseDao newDao) {
        if (Tool.isEmpty(dataList)) {
            return null;
        }
        List<ContentValues> newDataList = new ArrayList<>();
        HashMap<String, Field> newCacheMap = newDao.getCacheMap();
        for (T t : dataList) {
            ContentValues values = new ContentValues();
            for (Map.Entry<String, Field> entry : cacheMap.entrySet()) {
                Field value = entry.getValue();
                //字段名
                String columnName = entry.getKey();
                //变量名
                String fieldName = value.getName();
                //变量值
                String fieldValue = null;
                try {
                    Object o = value.get(t);
                    if (o != null) {
                        fieldValue = o.toString();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                //判断新、旧表的字段名或变量名是否相等
                for (Map.Entry<String, Field> newEntry : newCacheMap.entrySet()) {
                    //新表字段名
                    String newColumnName = newEntry.getKey();
                    //新表变量名
                    String newFieldName = newEntry.getValue().getName();
                    if (columnName.equals(newColumnName) || columnName.equals(newFieldName) ||
                            fieldName.equals(newColumnName) || fieldName.equals(newFieldName)) {
                        values.put(newColumnName, fieldValue);
                    }
                }
            }
            newDataList.add(values);
        }
        return newDataList;
    }

    public HashMap<String, Field> getCacheMap() {
        return cacheMap;
    }
}
