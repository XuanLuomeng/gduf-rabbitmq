package com.gduf.rabbitmq.utils;

/**
 * @author LuoXuanwei
 * @date 2023/9/19 21:33
 * 沉睡工具类
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
