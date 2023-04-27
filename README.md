## 边做项目边回顾

### 枚举类

1. 枚举类的属性

	- 枚举类对象属性不应允许被改动，所以使用`private final`修饰
	- 枚举类的使用`private final`修饰的属性在构造器中位其赋值
	- 枚举类显示的定义单参数的构造器，在列出枚举值是对应的传入参数

2. 枚举类的理解

	- 枚举类多用与定义一组常量，枚举类若只有一个对象，可以作为一种单例模式的实现方式

		> 一个枚举类本质上就是一个单例模式的实现。每个枚举值都是该枚举类型的单个实例，因此无论何时使用该枚举值，都是在访问同一个对象。

3. 枚举类的创建方式

	1. 使用enum关键字

	```java
	public enum ErrorCode {
	  	/**
	  	 *提供当前枚举类的多个对象
	  	 */
	   	SUCCESS(0, "ok",""),
	    PARAMS_ERROR(40000,"请求参数错误",""),
	    NULL_ERROR(40001,"请求数据为空", ""),
	    NOT_LOGIN(40100,"未登录",""),
	    NO_AUTH(40101,"无权限",""),
	    NO_USER(40102,"用户不存在",""),
	    SYSTEM_ERROR(50000, "系统内部异常", "");
	    /**
	     * 状态码
	     */
	    private final int code;
	
	    /**
	     * 状态码信息
	     */
	    private final String message;
	
	    /**
	     * 状态码描述(详情)
	     */
	    private final String description;
	
	    private ErrorCode(int code, String message, String description) {
	        this.code = code;
	        this.message = message;
	        this.description = description;
	    }
	
	    public int getCode() {
	        return code;
	    }
	
	    public String getMessage() {
	        return message;
	    }
	
	    public String getDescription() {
	        return description;
	    }
	}
	```

	

	2. 自定义枚举类

	```java
	class ErrorCode {
	  //区别在这里，使用enum关键字可以简化书写形式
	  public static final Errorcode SUCCESS = new ErrrorCode(0, "ok","");
	  
	  /**
	     * 状态码
	     */
	    private final int code;
	
	    /**
	     * 状态码信息
	     */
	    private final String message;
	
	    /**
	     * 状态码描述(详情)
	     */
	    private final String description;
	  
	  	private ErrorCode(int code, String message, String description) {
	        this.code = code;
	        this.message = message;
	        this.description = description;
	    }
	
	    public int getCode() {
	        return code;
	    }
	
	    public String getMessage() {
	        return message;
	    }
	
	    public String getDescription() {
	        return description;
	    }
	}
	```

	3. 两者的区别

		- 枚举值属性

			> 自定义枚举类可以添加各种类型的成员变量和方法，而使用`enum`关键字定义的枚举类型只能包含常量和方法。

		- 序列化支持

			> 使用`enum`关键字定义的枚举类型可以被序列化和反序列化，而自定义枚举类则需要对序列化和反序列化进行手动实现。

		- 实例化

			> 自定义枚举类可以使用`new`关键字实例化，而使用`enum`关键字定义的枚举类型只能使用预定义的常量来表示枚举值。

### 泛型类与泛型方法

> 泛型，就是允许在定义类、接口时通过一个标识 表示类中某个属性的类型或者是某个方法的返回值及参数类型。这个类型参数将在使用时(例如，继承或实现 这个接口，用这个类型声明变量、创建对象时)确定(即 传入实际的类型参数，也称为类型实参)。

1. 泛型类

	```java
	class 类名称 <泛型标识：可以随便写任意标识号，标识指定的泛型的类型>{
	  private 泛型标识 /*（成员变量类型）*/ var; 
	  .....
	
	  }
	}
	```

	- 泛型的类型参数只能是类类型，不能是简单类型。
	- 不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错。

	```java
	if(ex_num instanceof Generic<Number>){   } 
	```

2. 泛型方法

	```java
	/**
	 * 泛型方法的基本介绍
	 * @param tClass 传入的泛型实参
	 * @return T 返回值为T类型
	 * 说明：
	 *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
	 *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
	 *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
	 *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
	 */
	public <T> T genericMethod(Class<T> tClass)throws InstantiationException ,
	  IllegalAccessException{
	        T instance = tClass.newInstance();
	        return instance;
	}
	
	public class StaticGenerator<T> {
	    ....
	    ....
	    /**
	     * 如果在类中定义使用泛型的静态方法，需要添加额外的泛型声明（将这个方法定义成泛型方法）
	     * 即使静态方法要使用泛型类中已经声明过的泛型也不可以。
	     * 如：public static void show(T t){..},此时编译器会提示错误信息：
	          "StaticGenerator cannot be refrenced from static context"
	     */
	    public static <T> void show(T t){
	
	    }
	}
	```

	> 静态方法无法访问类上定义的泛型，是因为静态方法的执行不依赖于类的实例，而泛型是实例化时才确定的。换句话说，泛型是与类的实例相关联的，而静态方法并没有关联到任何实例。
	>
	> 在一个泛型类中，泛型参数只有在实例化过程中才会被具体化（即替换成实际类型），因此类上定义的泛型参数只能在实例方法中使用，而不能在静态方法中使用。如果需要在静态方法中使用泛型参数，则必须将其声明为静态泛型。

3. 泛型通配符

	```java
	public void showKeyValue1(Generic<?> obj){
	    Log.d("泛型测试","key value is " + obj.getKey());
	}
	```

	> 类型通配符一般是使用？代替具体的类型实参，注意了，此处’？’是类型实参，而不是类型形参 。此处的？和Number、String、Integer一样都是一种实际的类型，可以把？看成所有类型的父类。是一种真实的类型。

4. 泛型的上下边界

	```java
	//泛型类定义上边界
	class MyClass<T extends SomeClass> { ... }
	//泛型类定义下边界
	class MyClass<T super SomeClass> { ... }
	//泛型方法定义上下边界
	public <T extends SomeClass> T doSomething(OtherClass<T> other){}
	```

	> 在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加

- 总结

	> 无论何时，如果你能做到，你就该尽量使用泛型方法。也就是说，如果使用泛型方法将整个类泛型化，那么就应该使用泛型方法。另外对于一个static的方法而已，无法访问泛型类型的参数。所以如果static方法要使用泛型能力，就必须使其成为泛型方法。

### 异常处理器

### 拦截器

