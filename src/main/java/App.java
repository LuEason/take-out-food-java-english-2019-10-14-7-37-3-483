import java.util.LinkedList;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        /*String str = String.join(",", inputs);
        if (str.equals("ITEM0001 x 1,ITEM0013 x 2,ITEM0022 x 1")) {
            return "============= Order details =============\n" +
                    "Braised chicken x 1 = 18 yuan\n" +
                    "Chinese hamburger x 2 = 12 yuan\n" +
                    "Cold noodles x 1 = 8 yuan\n" +
                    "-----------------------------------\n" +
                    "Promotion used:\n" +
                    "Half price for certain dishes (Braised chicken��Cold noodles)��saving 13 yuan\n" +
                    "-----------------------------------\n" +
                    "Total��25 yuan\n" +
                    "===================================";
        } else if (str.equals("ITEM0013 x 4,ITEM0022 x 1")) {
            return "============= Order details =============\n" +
                    "Chinese hamburger x 4 = 24 yuan\n" +
                    "Cold noodles x 1 = 8 yuan\n" +
                    "-----------------------------------\n" +
                    "Promotion used:\n" +
                    "��30��6 yuan��saving 6 yuan\n" +
                    "-----------------------------------\n" +
                    "Total��26 yuan\n" +
                    "===================================";
        } else {
            return "============= Order details =============\n" +
                    "Chinese hamburger x 4 = 24 yuan\n" +
                    "-----------------------------------\n" +
                    "Total��24 yuan\n" +
                    "===================================";
        }*/
        List<Item> allItems = itemRepository.findAll();
        List<SalesPromotion> allSalesPromotions = salesPromotionRepository.findAll();
        SalesPromotion halfPricePromotion = allSalesPromotions.get(1);
        List<String> halfPricePromotionRelatedItems = halfPricePromotion.getRelatedItems();
        String returnString = "============= Order details =============\n";
        List<String> halfPriceItemsName = new LinkedList<>();
        int totalPrice = 0;
        int halfTotalPrice = 0;
        boolean hasHalfPriceItem = false;
        for (String input : inputs) {
            for (Item item : allItems) {
                String[] inputSplit = input.split(" x ");
                if (item.getId().equals(inputSplit[0])) {
                    int itemPrice = (int)item.getPrice() * Integer.parseInt(inputSplit[1]);
                    if (halfPricePromotionRelatedItems.contains(item.getId())) {
                        halfPriceItemsName.add(item.getName());
                        halfTotalPrice += itemPrice / 2;
                        hasHalfPriceItem = true;
                    } else {
                        halfTotalPrice += itemPrice;
                    }
                    totalPrice += itemPrice;
                    returnString += item.getName() + " x " +
                            inputSplit[1] + " = " + itemPrice + " yuan\n";
                    break;
                }
            }
        }
        returnString += "-----------------------------------\n";
        int deductPrice = totalPrice;
        if (deductPrice >= 30) {
            deductPrice -= 6;
        }
        if (!hasHalfPriceItem) {
            if (deductPrice == totalPrice) {
                returnString += "Total��" + totalPrice + " yuan\n" +
                        "===================================";
            } else {
                returnString += "Promotion used:\n" +
                        "��30��6 yuan��saving 6 yuan\n" +
                        "-----------------------------------\n" +
                        "Total��" + deductPrice + " yuan\n" +
                        "===================================";
            }
        } else {
            if (deductPrice == totalPrice) {
                returnString += "Total��" + totalPrice + " yuan\n" +
                        "===================================";
            } else if (deductPrice <= halfTotalPrice) {
                returnString += "Promotion used:\n" +
                        "��30��6 yuan��saving 6 yuan\n" +
                        "-----------------------------------\n" +
                        "Total��" + deductPrice + " yuan\n" +
                        "===================================";
            } else {
                returnString += "Promotion used:\n" + "Half price for certain dishes (" +
                        String.join("��", halfPriceItemsName) +
                        ")��saving " + (totalPrice - halfTotalPrice) +
                        " yuan\n" + "-----------------------------------\n" +
                        "Total��" + halfTotalPrice +  " yuan\n" +
                        "===================================";
            }
        }
        return returnString;
    }
}
