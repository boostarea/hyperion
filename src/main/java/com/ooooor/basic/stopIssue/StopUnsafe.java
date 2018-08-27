package com.ooooor.basic.stopIssue;

/**
 * stop 导致写数据到一半，被强行终止，导致对象数据被破坏
 *
 */
public class StopUnsafe {

    public static User user = new User();

    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User id = " + id + ", name=" + name;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                synchronized (user) {
                    if (user.getId() != Integer.parseInt(user.getName())) {
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }).start();



        while (true) {
            Thread change = new Thread(() -> {
                while (true) {
                    synchronized (user) {
                        int v = (int) System.currentTimeMillis() / 100;
                        user.setId(v);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        user.setName(String.valueOf(v));
                    }
                    Thread.yield();
                }
            });
            change.start();
            Thread.sleep(150);
            change.stop();
        }
    }


}