import java.util.HashMap ;
import java.util.Set ;

public class MyDeleteKeyObject{
	public static void main(String[] args) {
		HashMap<Price , String > hm = new HashMap<>() ;
		hm.put(new Price("Banana" , 20) , "Banana") ;
		hm.put(new Price("apple" , 20) , "apple") ;
		hm.put(new Price("orange" , 20) , "orange") ;
		hm.put(new Price("mango" , 20) , "mango") ;

		printMap(hm) ;
		Price key = new Price("Banana" , 20) ;

		System.out.println("Does key available ? " + hm.containsKey(key) ) ;
		System.out.println("Does value available ? " + hm.containsValue("Banana") )  ;

		System.out.println("Deleting key ...... " );
		hm.remove(key) ;
		System.out.println("After Deleting key ....... " ) ;
		printMap(hm) ;
	}

	public static void printMap( HashMap<Price , String> map ){
		Set<Price> keys = map.keySet() ;
		for(Price p : keys){
			System.out.println(p + " ==> " + map.get(p)) ;
		}
	}
}

class Price {
	private String item ;
	private int price ;

	public Price(String item , int price){
		this.item = item ;
		this.price = price ;
	}
	public int hashCode(){
		// the value of hashcode we are returning should depend upon all the fields of the key inserted into map
		System.out.println("In hashcode") ;
		int hashcode =  0 ;
		hashcode = price * 20 ;
		hashcode += item.hashCode() ;
		return hashcode ;
	}
	public boolean equals(Object obj ){
		System.out.println("In equals") ;

		if( obj instanceof Price ){
			Price pp = (Price)obj ;
			return (pp.item.equals(this.item) && pp.price == this.price ) ;
		}else{
			return false ;
		}
	}
	public String toString(){
		return "item : " + item + "price : " + price ;
	}
}


/*
    Output :
    =======

In hashcode
In hashcode
In hashcode
In hashcode
In hashcode
item : orangeprice : 20 ==> orange
In hashcode
item : appleprice : 20 ==> apple
In hashcode
item : Bananaprice : 20 ==> Banana
In hashcode
item : mangoprice : 20 ==> mango
In hashcode
In equals
Does key available ? true
Does value available ? true
Deleting key ......
In hashcode
In equals
After Deleting key .......
In hashcode
item : orangeprice : 20 ==> orange
In hashcode
item : appleprice : 20 ==> apple
In hashcode
item : mangoprice : 20 ==> mango

*/


/*
    
==> WHY WE SHOULD OVERRIDE equals() and hashcode()  ??
    ===============================================    

Joshua Bloch says on Effective Java
You must override hashCode() in every class that overrides equals(). Failure to do so will result in a violation of the general contract
for Object.hashCode(), which will prevent your class from functioning properly in conjunction with all hash-based collections,
including HashMap, HashSet, and Hashtable.

Let's try to understand it with an example of what would happen if we override equals() without overriding hashCode() and attempt
to use a Map.

Say we have a class like this and that two objects of MyClass are equal if their importantField is equal (with hashCode() and equals()
generated by eclipse)

public class MyClass {

    private final String importantField;
    private final String anotherField;

    public MyClass(final String equalField, final String anotherField) {
        this.importantField = equalField;
        this.anotherField = anotherField;
    }

    public String getEqualField() {
        return importantField;
    }

    public String getAnotherField() {
        return anotherField;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((importantField == null) ? 0 : importantField.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MyClass other = (MyClass) obj;
        if (importantField == null) {
            if (other.importantField != null)
                return false;
        } else if (!importantField.equals(other.importantField))
            return false;
        return true;
    }

}


Override only equals
====================

If only equals is overriden, then when you call myMap.put(first,someValue) first will hash to some bucket and when you call
myMap.put(second,someOtherValue) it will hash to some other bucket (as they have a different hashCode). So, although they 
are equal, as they don't hash to the same bucket, the map can't realize it and both of them stay in the map.

Although it is not necessary to override equals() if we override hashCode(), let's see what would happen in this particular
case where we know that two objects of MyClass are equal if their importantField is equal but we do not override equals().

Override only hashCode
======================

Imagine you have this

MyClass first = new MyClass("a","first");
MyClass second = new MyClass("a","second");
If you only override hashCode then when you call myMap.put(first,someValue) it takes first, calculates its hashCode and 
stores it in a given bucket. Then when you call myMap.put(second,someOtherValue) it should replace first with second as
per the Map Documentation because they are equal (according to the business requirement).

But the problem is that equals was not redefined, so when the map hashes second and iterates through the bucket looking 
if there is an object k such that second.equals(k) is true it won't find any as second.equals(first) will be false.

Hope it was clear

*/