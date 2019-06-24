package com.tanglover.study.concurrent;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author TangXu
 * @create 2019-05-22 15:29
 * @description:
 */
public class BatchInsertTest {
    public static void main(String[] args) {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setMaxPoolSize(10);
        poolTaskExecutor.setCorePoolSize(3);
        poolTaskExecutor.initialize();
        poolTaskExecutor.execute(BatchInsertTest::batchInsert);
    }

    private static void batchInsert() {
        String url = "jdbc:mysql://localhost:3306/trh_bill?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String name = "com.mysql.jdbc.Driver";
        String user = "root";
        String password = "system";
        Connection conn = null;
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            // 关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        if (conn == null) {
            return;
        }
        Long begin = System.currentTimeMillis();
        String prefix = "INSERT INTO `trh_bill`.`invest_coupon` (`investor_id`, `coupon_id`, `coupon_name`, `acticity_id`, `acticity_name`, `coupon_type`, `coupon_project`, `coupon_project_min_day`, `coupon_project_max_day`, `coupon_time_rule`, `repayment_type`, `coupon_start_time`, `coupon_end_time`, `put_off_day`, `money_rule`, `invert_money`, `can_cash_money`, `rate_num`, `rate_start_money`, `coupon_money`, `coupon_rule`, `coupon_deadline`, `state`, `invest_record_id`, `extend1`, `extend2`, `specified`, `create_time`, `update_time`, `presenter`, `presente_time`) VALUES ";
        StringBuffer suffix;
        try {
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement("");
            // 外层控制总提交事务次数
            for (int i = 1; i <= 10; i++) {
                suffix = new StringBuffer();
                for (int j = 1; j <= 10; j++) {
//                    List<String> values = Lists.newArrayList(i * j + "", 1 + "", "第" + i * j + "个用户");
//                    suffix.append("('555', '1', '新手任务', '1', '', '1', '2', '30', '9999', '2', '-1', '1541473372170', '1544111999000', '30', '2', '0', '1000000', '0.00', '0', '8800', '有效期为30天及以上，非新手标，可出借选择抵扣，如遇到出借失败则退回', '1544111999000', '2', '0', '', '', '0', '1541473372170', '1541473372170', '0', '0') ,");
//                    ArrayList<Invest_coupon> invest_coupons = Lists.newArrayList(new Invest_coupon());
//                    suffix.append("('").append(Joiner.on("','").join(invest_coupons)).append("'),");
                }
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                pst.addBatch(sql);
                pst.executeBatch();
                conn.commit();
            }
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        // 耗时
        System.out.println("100万条数据插入花费时间: " + (end - begin) / 1000 + " s" + "插入完成");
    }
}