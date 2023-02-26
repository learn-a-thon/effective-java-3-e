package item13.clone_use_constructor;

public class Item implements Cloneable {

    private String name;

    /**
     * 이렇게 구현하면 하위 클래스의 clone()이 깨질 수 있다. p78
     * @return
     */
    @Override
    public Item clone() {
        Item item = new Item();
        item.name = this.name;
        return item;
    }

    /**
     * 불변 객체인 경우 올바른 clone() 재정의 방법
     * @return
     */
//    @Override
//    public Item clone() {
//        Item result = null;
//        try {
//            result = (Item) super.clone();
//            return result;
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
