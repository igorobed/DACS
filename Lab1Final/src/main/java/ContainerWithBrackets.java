import java.util.List;

public class ContainerWithBrackets {
    //все переменные в обоих классах надо заприватить и сделать методы доступа к ним
    //но пока для простоты пусть будут паблик
    public List<BracketsLeftRight> bracket;

    public static class BracketsLeftRight {
        public String left;
        public String right;

        public BracketsLeftRight(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }

    public ContainerWithBrackets(List<BracketsLeftRight> bracket) {
        this.bracket = bracket;
    }
    public ContainerWithBrackets() {
        this.bracket = null;
    }
}
