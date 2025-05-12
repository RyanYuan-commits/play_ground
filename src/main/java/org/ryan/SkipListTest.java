package org.ryan;

import lombok.Data;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author Ryan Yuan
 * @Description
 * @Create 2025-03-31 14:10
 */
public class SkipListTest {

    public static void main(String[] args) {
        SkipList<String> skipList = new SkipList<>();
        skipList.add(1, "🍎");
        skipList.add(2, "🍊");
        skipList.add(3, "🍌");
        skipList.add(4, "🐶");
        skipList.add(5, "🫎");
        System.out.println(skipList.search(2));
        skipList.printList();

        System.out.println("=========");

        skipList.delete(5);
        skipList.printList();
    }

    @Data
    static class SkipNode<T> {

        int key;

        T value;

        SkipNode<T> right, down;

        public SkipNode (int key, T value) {
            this.key = key;
            this.value = value;
        }

    }

    static class SkipList<T> {

        /**
         * 头节点，入口
         */
        SkipNode<T> headNode;

        /**
         * 当前跳表索引层数
         */
        int highLevel;

        /**
         * 抛硬币
         */
        ThreadLocalRandom random;

        /**
         * 最大层数
         */
        final int MAX_LEVEL = 32;

        public SkipList(){
            random = ThreadLocalRandom.current();
            headNode = new SkipNode<>(Integer.MIN_VALUE, null);
            highLevel = 1;
        }

        public SkipNode<T> search(int key) {
            // 从头节点开始查询
            SkipNode<T> temp = headNode;
            while (temp != null) {
                if (temp.key == key) return temp;
                // 右侧没有了，从下层开始寻找
                else if (temp.right == null) temp = temp.down;
                // 定位到一个区间，向下进行查询
                else if (temp.right.key > key) temp = temp.down;
                else temp = temp.right;
            }
            return null;
        }

        public void delete(int key) {
            SkipNode<T> temp = headNode;
            while (temp != null) {
                if (temp.right == null) temp = temp.down;
                // 找到了需要删除的节点，在节点的右侧
                else if (temp.right.key == key) {
                    temp.right = temp.right.right;
                    temp = temp.down;
                }
                else if (temp.right.key > key) temp = temp.down;
                else temp = temp.right;
            }
        }

        public void add(SkipNode<T> node) {
            int key;
            SkipNode<T> temp;
            // 如果已存在，更新
            if ((temp = search(key = node.key)) != null) {
                while (temp != null) {
                    // 从 temp 开始查询
                    temp.value = node.value;
                    temp = temp.down;
                }
                return;
            }

            // 存储需要插入的节点
            Stack<SkipNode<T>> stack = new Stack<>();
            // 从 header 开始寻找，找到插入的位置
            temp = headNode;
            while (temp != null) {
                if (temp.right == null || temp.right.key > key) {
                    stack.add(temp);
                    temp = temp.down;
                } else temp = temp.right;
            }

            int level = 1;
            // 上层节点需要指向的节点
            SkipNode<T> downNode = null;
            while (!stack.isEmpty()) {
                SkipNode<T> pop = stack.pop();
                // 处理竖向
                SkipNode<T> newNode = new SkipNode<>(node.key, node.value);
                newNode.down = downNode;
                downNode = newNode;

                // 处理横向
                newNode.right = pop.right;
                pop.right = newNode;

                // 查看是否还需要向上
                if (level > MAX_LEVEL) break;
                double num = random.nextDouble();
                if(num > 0.5) break;

                // 需要继续向上
                level++;
                if (level > highLevel) {
                    highLevel = level;
                    SkipNode<T> newHead = new SkipNode<>(Integer.MIN_VALUE, null);
                    newHead.down = headNode;
                    headNode = newHead;
                    stack.add(headNode);
                }
            }
        }

        public void add(int key, T value) {
            add(new SkipNode<>(key, value));
        }

        public void printList() {
            SkipNode<T> temp = headNode;
            String format = "(%s, %s)";
            // 存储每一行的开头
            SkipNode<T> listHead = temp;
            while (listHead != null && temp != null) {
                System.out.print(String.format(format, temp.key, temp.value) + " ->" + "\t");
                if (temp.right == null) {
                    temp = listHead.down;
                    listHead = listHead.down;
                    System.out.print("\n");
                } else {
                    int num = -1;
                    int rightKey = temp.right.key;
                    SkipNode<T> down = temp.down;
                    while (down != null && down.key != rightKey) {
                        down = down.right;
                        num++;
                    }
                    for (int i = 0; i < num; i++) {
                        System.out.print("--------->" + "\t");
                    }
                    temp = temp.right;
                }
            }
        }
    }

}
