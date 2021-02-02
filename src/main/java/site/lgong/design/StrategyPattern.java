package site.lgong.design;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 策略模式：Strategy，是指，定义一组算法，并把其封装到一个对象中。然后在运行时，可以灵活的使用其中的一个算法
 */
public class StrategyPattern {

    public static void main(String[] args) {
        DiscountContext ctx = new DiscountContext();

        // 默认使用普通会员折扣:
        BigDecimal pay1 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay1);

        // 使用满减折扣:
        ctx.setStrategy(new OverDiscountStrategy());
        BigDecimal pay2 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay2);

        // 使用Prime会员折扣:
        ctx.setStrategy(new PrimeDiscountStrategy());
        BigDecimal pay3 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay3);

        // 使用满减+Prime会员折扣:
        ctx.setStrategy(new OverAndPrimeDiscountStrategy());
        BigDecimal pay4 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay4);
    }

}

/**
 * 折扣策略接口-比如会有打折策略，满减策略
 */
interface DiscountStrategy {
    // 计算折扣额度:
    BigDecimal getDiscount(BigDecimal total);
}

/**
 * 普通会员策略
 */
class UserDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 普通会员打九折:
        return total.multiply(new BigDecimal("0.1")).setScale(2, RoundingMode.DOWN);
    }
}

/**
 * 高级会员策略
 */
class PrimeDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 高级会员打八折:
        return total.multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.DOWN);
    }
}

/**
 * 满减策略
 */
class OverDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 满100减20优惠:
        return total.compareTo(BigDecimal.valueOf(100)) >= 0 ? BigDecimal.valueOf(20) : BigDecimal.ZERO;
    }
}

/**
 * 100减20的基础上再对高级会员进行折扣
 */
class OverAndPrimeDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 满100减20优惠:
        BigDecimal count = BigDecimal.valueOf(0);
        count = count.add(new OverDiscountStrategy().getDiscount(total));
        count = count.add(new PrimeDiscountStrategy().getDiscount(total.subtract(count)));
        return count;
    }
}

/**
 * 应用策略
 */
class DiscountContext {
    // 默认持有某个策略:普通用户折扣
    private DiscountStrategy strategy = new UserDiscountStrategy();

    // 允许客户端设置新策略:
    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    // 计算折扣后的价格
    public BigDecimal calculatePrice(BigDecimal total) {
        //总价减去优惠价格=优惠后的价格
        return total.subtract(this.strategy.getDiscount(total)).setScale(2);
    }
}

