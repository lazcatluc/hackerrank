

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    private final int cases;
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
        this.cases = this.in.nextInt();
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }
    
    public static int findMaximum(int[] prices, int startIndex) {
        if (startIndex >= prices.length) {
            return 0;
        }
        int max = prices[startIndex];
        int myIndex = startIndex + 1;
        while (myIndex < prices.length) {
            if (prices[myIndex] > max) {
                max = prices[myIndex];
            }
            myIndex++;
        }
        return max;
    }
    
    public int solveOne() {
        int pricesCount = in.nextInt();
        int[] prices = new int[pricesCount];
        IntStream.range(0, pricesCount).forEach(i -> prices[i] = in.nextInt());
        PortfolioBuilder portfolioBuilder = new PortfolioBuilder();
        int index = 0;
        int max = findMaximum(prices, 0);
        while (index < prices.length) {
            Portfolio portfolio = portfolioBuilder.stockPrice(prices[index]).build();
            if (prices[index] == max) {
                portfolioBuilder = portfolio.sell();
                max = findMaximum(prices, index + 1);
            }
            else {
                portfolioBuilder = portfolio.buy();
            }
            index++;
        }
        return portfolioBuilder.build().getValue();
    }

    static class Portfolio {
        private final int stocks;
        private final int money;
        private final int stockPrice;
        
        Portfolio(PortfolioBuilder builder) {
            this.stocks = builder.stocks;
            this.money = builder.money;
            this.stockPrice = builder.stockPrice;
        }

        public int getValue() {
            return money + stocks * stockPrice;
        }
        
        public PortfolioBuilder buy() {
            return new PortfolioBuilder().stocks(stocks + 1).money(money - stockPrice);
        }
        
        public PortfolioBuilder sell() {
            return new PortfolioBuilder().stocks(0).money(getValue());
        }
        
        public PortfolioBuilder hold() {
            return new PortfolioBuilder().stocks(stocks).money(money);
        }
    }
    
    static class PortfolioBuilder {
        private int stocks;
        private int money;
        private int stockPrice;
        
        public PortfolioBuilder stocks(int stocks) {
            this.stocks = stocks;
            return this;
        }
        
        public PortfolioBuilder money(int money) {
            this.money = money;
            return this;
        }
        
        public PortfolioBuilder stockPrice(int stockPrice) {
            this.stockPrice = stockPrice;
            return this;
        }
        
        public Portfolio build() {
            return new Portfolio(this);
        }
    }
    
    public void solve() {
        IntStream.range(0, cases).forEach(i -> out.println(solveOne()));
    }
}
