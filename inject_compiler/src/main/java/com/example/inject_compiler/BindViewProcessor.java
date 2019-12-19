package com.example.inject_compiler;

import com.example.inject_annotion.BindView;
import com.example.inject_compiler.FieldViewBinding;
import com.example.inject_compiler.FileUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


//在Eclipse中 需要在main文件夹下 新建META-INF/service文件夹
//再新建javax.annotion.process.Prosser去指定注解处理器,在AndroidStudio用@AutoService 注解可以达到一样的效果
@AutoService(Processor.class)
public class BindViewProcessor  extends AbstractProcessor {
    /**
     处理Element工具类
     主要获取包名
     */
    private Elements elementUtils;
    /**
     * Java文件输出类（非常重要）生成它是用来java文件
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils=processingEnvironment.getElementUtils();
        filer=processingEnvironment.getFiler();
    }

    /**
     * @return
     *当前注解处理器  支持哪些注解
     * 返回的是set字符串集合
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types=new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
//        types.add(Override.class.getCanonicalName());
        return types;
    }

    /**
     * 支持jdk版本
     * 一般选择最新版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * javac编译器  遇到含有BindView注解的java文件时，就会调用这个process方法
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //声明一个缓存的map集合   键  是Activity   值  是当前Activity 里面含有BndView成员变量的集合
        Map<TypeElement,List<FieldViewBinding>> targetMap=new HashMap<>();
        //编译时 只能通过文件输出打印，不能通过Log，System.out.print打印
//        FileUtils.print("------------>    ");
        //for循环带有BindView注解的Element
        //将每个Element进行分组。分组的形式 是将在一个Activity的Element分为一组   最后按照activiyt的数量创建对应的文件数量
        for(Element element:roundEnvironment.getElementsAnnotatedWith(BindView.class))
        {
//            FileUtils.print("elment   "+element.getSimpleName().toString());
//         enClosingElement可以理解成Element
            TypeElement enClosingElement= (TypeElement) element.getEnclosingElement();
            // List<FieldViewBinding>   当前Activity 含有注解的成员变量集合
            List<FieldViewBinding> list=targetMap.get(enClosingElement);
            if(list==null)
            {
                list=new ArrayList<>();
                targetMap.put(enClosingElement,list);//
            }
            //得到包名
            String packageName=getPackageName(enClosingElement);
            //得到id
            int id=element.getAnnotation(BindView.class).value();
//            得到成员变量名  TextView  text;  这里得到的是text字符串
            String fieldName=element.getSimpleName().toString();
//            当前成员变量的类类型   可以理解成  TextView  通过Element的asType()获得元素的类型
            TypeMirror typeMirror=element.asType();
//            封装成FieldViewBinding  类型
            FieldViewBinding fieldViewBinding=new FieldViewBinding(fieldName,typeMirror,id);
            list.add(fieldViewBinding);
        }
        //遍历每一个Activity  TypeElement代表类类型
        for(Map.Entry<TypeElement,List<FieldViewBinding>> item:targetMap.entrySet())
        {
            List<FieldViewBinding> list=item.getValue();

            if(list==null||list.size()==0)
            {
                continue;
            }
            //enClosingElement 表示 activity
            TypeElement enClosingElement=item.getKey();
//            得到包名
            String packageName=getPackageName(enClosingElement);
            //截取字符串    MainActivity
            String complite=getClassName(enClosingElement,packageName);
            //遵循Javapoet规范，MainActivity为类类型  在这里封装成ClassName
            ClassName className=ClassName.bestGuess(complite);
//          ViewBinder类型
            ClassName  viewBinder=ClassName.get("com.example.inject.compile","ViewBinder");
//            开始构建java文件
//            从外层包名  类名开始构建
            TypeSpec.Builder result=TypeSpec.classBuilder(complite+"$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T",className))
                    .addSuperinterface(ParameterizedTypeName.get(viewBinder,className));
//          构建方法名
            MethodSpec.Builder methodBuilder=MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addAnnotation(Override.class)
                    .addParameter(className,"target",Modifier.FINAL);
//            构建方法里面的具体逻辑，这里的逻辑代表findViewById
            for(int i=0;i<list.size();i++)
            {
                FieldViewBinding fieldViewBinding=list.get(i);
                //-->android.text.TextView
                String pacckageNameString=fieldViewBinding.getType().toString();
                ClassName viewClass=ClassName.bestGuess(pacckageNameString);
                //$L  代表占位符  和StringFormater类似。$L代表基本类型  $T代表  类类型    方法内部添加代码
                methodBuilder.addStatement
                        ("target.$L=($T)target.findViewById($L)",fieldViewBinding.getName()
                                ,viewClass,fieldViewBinding.getResId());
            }

            result.addMethod(methodBuilder.build());

            try {
                //生成Java文件，最终写是通过filer类写出的
                JavaFile.builder(packageName,result.build())
                        .addFileComment("auto create make wxq  wxq")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return false;
    }


//    public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<MainActivity> {
//        @Override
//        public void bind(final MainActivity target) {
//            target.tv_build_annotation=(TextView)target.findViewById(2131297039);
//        }
//    }



    //enClosingElement.getQualifiedName().toString()返回的是com.example.administrator.butterdepends.MainActivity
    private String getClassName(TypeElement enClosingElement, String packageName) {
        int packageLength=packageName.length()+1;
//        replace(".","$")的意思是  如果当前的enClosing为内部类的话
//       裁剪掉包名和最后一个点号，将去掉包名后，后面还有点号则替换成$符号
        return enClosingElement.getQualifiedName().toString().substring(packageLength).replace(".","$");
    }

    private String getPackageName(TypeElement enClosingElement) {
        //返回的是  com.example.administrator.butterknifeframwork。通过工具类获取的
        return   elementUtils.getPackageOf(enClosingElement).getQualifiedName().toString();
    }
}


//相关连接 参考连接

//https://www.jianshu.com/p/8db4d42a47e3    通过Element的getEnclosingElement返回元素的父元素。

//javeopt 创建java文件
//https://github.com/square/javapoet

//https://www.jianshu.com/p/4a276b671c8a  写法和当前的差不多

// element 相关文档
//https://tool.oschina.net/uploads/apidocs/jdk-zh/javax/lang/model/element/Element.html

// 简单介绍各自注解
//https://www.jianshu.com/p/f1a8356c615f


/**
 * public interface ProcessingEnvironment {
 *
 *     Map<String,String> getOptions();
 *
 *     //Messager用来报告错误，警告和其他提示信息
 *     Messager getMessager();
 *
 *     //Filter用来创建新的源文件，class文件以及辅助文件
 *     Filer getFiler();
 *
 *     //Elements中包含用于操作Element的工具方法
 *     Elements getElementUtils();
 *
 *     //Types中包含用于操作TypeMirror的工具方法
 *     Types getTypeUtils();
 *
 *     SourceVersion getSourceVersion();
 *
 *     Locale getLocale();
 * }
 *
 * VariableElement 代表一个 字段, 枚举常量, 方法或者构造方法的参数, 局部变量及 异常参数等元素
 *         PackageElement	代表包元素
 *         TypeElement	代表类或接口元素
 *         ExecutableElement
 *
 *
 */



