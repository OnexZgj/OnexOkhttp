package com.lnsoft.okhttp.onexokhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * des：线程池管理类
 * author：onexzgj
 * time：2019/1/23
 */
public class TheadPoolManager {


    //1、线程池队列（存放请求）
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();


    //2、失败重试队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();


    public void addDelayTask(HttpTask task) {
        if (task != null) {
            task.setDelayTime(3000);
            mDelayQueue.offer(task);
        }
    }


    private static TheadPoolManager manager = new TheadPoolManager();

    public static TheadPoolManager getInstance() {
        return manager;
    }


    public Runnable mDelayRunnable = new Runnable() {
        @Override
        public void run() {
            HttpTask take = null;
            while (true) {
                try {
                    take = mDelayQueue.take();
                    if (take.getRetryCount() < 3) {
                        executor.execute(take);
                        take.setRetryCount(take.getRetryCount() + 1);
                        Log.d("TAG", "重试机制: " + take.getRetryCount());
                    } else {
                        Log.d("TAG", "老是出错,请求出错...");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    //2、添加任务到线程池
    public void addRequestTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //3、创建线程池
    private ThreadPoolExecutor executor;

    public TheadPoolManager() {
        //第四个参数 线程池内部维护的队列
        executor = new ThreadPoolExecutor(3, 10,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                        addRequestTask(runnable);
                        //TODO 新的操作
                    }
                });

        executor.execute(mRunnalbe);
        executor.execute(mDelayRunnable);
    }


    //4、循环从队列中进行拿出线程进行执行
    public Runnable mRunnalbe = new Runnable() {
        @Override
        public void run() {
            Runnable runn = null;
            while (true) {
                try {
                    runn = mQueue.take();
                    executor.execute(runn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}
