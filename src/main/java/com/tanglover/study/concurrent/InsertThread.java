package com.tanglover.study.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author TangXu
 * @create 2019-05-22 14:12
 * @description:
 */
public class InsertThread {

    private static final Logger logger = LoggerFactory.getLogger(TestConcurrent.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static Connection conn = null;

    public static void insert() {
        logger.info("线程开始时间: {}", sdf.format(System.currentTimeMillis()));
        conn = MyConnection.getInstance().getConnection();
        String sql = "INSERT INTO `trh_bill`.`invest_coupon` (`investor_id`, `coupon_id`, `coupon_name`, `acticity_id`, `acticity_name`, `coupon_type`, `coupon_project`, `coupon_project_min_day`, `coupon_project_max_day`, `coupon_time_rule`, `repayment_type`, `coupon_start_time`, `coupon_end_time`, `put_off_day`, `money_rule`, `invert_money`, `can_cash_money`, `rate_num`, `rate_start_money`, `coupon_money`, `coupon_rule`, `coupon_deadline`, `state`, `invest_record_id`, `extend1`, `extend2`, `specified`, `create_time`, `update_time`, `presenter`, `presente_time`) VALUES ('555', '1', '新手任务', '1', '', '1', '2', '30', '9999', '2', '-1', '1541473372170', '1544111999000', '30', '2', '0', '1000000', '0.00', '0', '8800', '有效期为30天及以上，非新手标，可出借选择抵扣，如遇到出借失败则退回', '1544111999000', '2', '0', '', '', '0', '1541473372170', '1541473372170', '0', '0')";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            boolean execute = ps.execute();
            logger.info("执行结果：{}", execute);
            MyConnection.close(conn);
            logger.info("线程结束时间: {}", sdf.format(System.currentTimeMillis()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}