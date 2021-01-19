package shared.city;

import java.io.Serializable;

/**
 * класс человека
 */
public class Human implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private float height; //Значение поля должно быть больше 0

    public Human(String name, float height) {
        this.name = name;
        this.height = height;
    }

    public String toString(){
        return(this.name+" "+String.valueOf(this.height));
        }
        public  String getName(){
        return(this.name);
        }
    public  Float getHeight(){
        return(this.height);
    }
    }

