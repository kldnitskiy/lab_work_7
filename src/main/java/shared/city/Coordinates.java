package shared.city;

import java.io.Serializable;

/**
 * класс координат
 */
public class Coordinates implements Serializable {

        /**
         * Поле координаты x,
         */
        private float x;

        /**
         * Поле координаты y
         */
        private long y;

        /**
         * Конструктор для задания координат x и y
         *
         * @param x - координата x
         * @param y - координата y
         */
        public Coordinates(float x, long y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Метод для получения координаты y
         *
         * @return возвращает значение координаты y
         */
        public long getY() {
            return y;
        }

        /**
         * Метод для получения координаты x
         *
         * @return возвращает значение координаты x
         */
        public float getX() {
            return x;
        }

        /**
         * Метод для получения координат организации в строчной форме в формате "X = A Y = B"
         *
         * @return возвращает строку координат в формате "A B"
         */
        @Override
        public String toString() {
            return x +" "+ y;
        }

    }
