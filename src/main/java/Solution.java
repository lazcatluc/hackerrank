

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {
    
    private final Scanner in;
    private final PrintStream out;
    private final String[] args;
    
    static class BinaryTree<T> {
        private final T root;
        private BinaryTree<T> left;
        private BinaryTree<T> right;
        
        public BinaryTree(T root) {
            this.root = root;
        }
        
        public Stream<T> inorder() {
            Stream<T> leftStream = left == null ? Stream.empty() : left.inorder();
            Stream<T> rightStream = right == null ? Stream.empty() : right.inorder();
            return Stream.concat(Stream.concat(leftStream, Stream.of(root)), rightStream);
        }
        
        public void swapChildren() {
            BinaryTree<T> newLeft = right;
            right = left;
            left = newLeft;
        }
    }
    
    public Solution(InputStream in, OutputStream out, String[] args) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.args = args;
    }

    public static void main(String[] args) {
        new Solution(System.in, System.out, args).solve();
    }
    
    @SuppressWarnings("unchecked")
    public BinaryTree<Integer> readTree() {
        int nodes = in.nextInt();
        List<BinaryTree<Integer>> list = IntStream.range(0, nodes).map(i -> i + 1)
                    .mapToObj(BinaryTree::new).collect(Collectors.toList());
        list.forEach(tree -> {
            int left = in.nextInt();
            if (left > -1) {
                tree.left = list.get(left - 1);
            }
            int right = in.nextInt();
            if (right > -1) {
                tree.right = list.get(right - 1);
            }
        });
        return list.get(0);
    }
    
    public <T> BinaryTree<T> swap(BinaryTree<T> tree, int depth, int originalDepth) {
        if (tree == null || depth == 0) {
            return tree;
        }
        if (depth == 1) {
            tree.swapChildren();
            swap(tree.left, originalDepth, originalDepth);
            swap(tree.right, originalDepth, originalDepth);
        }
        else {
            tree.left = swap(tree.left, depth - 1, originalDepth);
            tree.right = swap(tree.right, depth - 1, originalDepth);
        }
        return tree;
    }
    
    public <T> void printTree(BinaryTree<T> tree) {
        tree.inorder().forEach(t -> out.print(t + " "));
    }

    public void solve() {
        BinaryTree<Integer> tree = readTree();
        int swaps = in.nextInt();
        for (int i = 0; i < swaps; i++) {
            int depth = in.nextInt();
            tree = swap(tree, depth, depth);
            printTree(tree);
            out.println();
        };
    }
}
