package com.tanglover.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TangXu
 * @create 2019-05-22 11:46
 * @description:
 */
public class TestConcurrent {

    private static final Logger logger = LoggerFactory.getLogger(TestConcurrent.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Connection conn = null;
    int count = 0;

    private static ExecutorService pool = new ThreadPoolExecutor(20, 100, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue(10000));

//    @Test
    public void test() {
        pool.execute(InsertThread::insert);
    }

    public void concurrent() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                logger.info("线程开始时间: {}", sdf.format(start));
                conn = MyConnection.getInstance().getConnection();
                String sql = "INSERT INTO `trh_bill`.`invest_coupon` (`investor_id`, `coupon_id`, `coupon_name`, `acticity_id`, `acticity_name`, `coupon_type`, `coupon_project`, `coupon_project_min_day`, `coupon_project_max_day`, `coupon_time_rule`, `repayment_type`, `coupon_start_time`, `coupon_end_time`, `put_off_day`, `money_rule`, `invert_money`, `can_cash_money`, `rate_num`, `rate_start_money`, `coupon_money`, `coupon_rule`, `coupon_deadline`, `state`, `invest_record_id`, `extend1`, `extend2`, `specified`, `create_time`, `update_time`, `presenter`, `presente_time`) VALUES ('555', '1', '新手任务', '1', '', '1', '2', '30', '9999', '2', '-1', '1541473372170', '1544111999000', '30', '2', '0', '1000000', '0.00', '0', '8800', '有效期为30天及以上，非新手标，可出借选择抵扣，如遇到出借失败则退回', '1544111999000', '2', '0', '', '', '0', '1541473372170', '1541473372170', '0', '0')";
                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    System.out.println(1);
//                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trh_bill?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "system");
//                    System.out.println(conn);
                    PreparedStatement ps = conn.prepareStatement(sql);
                    boolean execute = ps.execute();
                    if (execute == false) count++;
                    logger.info("执行结果：{}", execute);
                    MyConnection.close(conn);
                    long end = System.currentTimeMillis();
                    logger.info("线程结束时间: {}", sdf.format(end));
                    logger.info("线程花费时间: {} ms", end - start);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}