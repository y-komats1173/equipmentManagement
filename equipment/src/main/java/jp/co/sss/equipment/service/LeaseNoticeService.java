package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.mapper.LeaseMapper;

/**
 * リース返却通知メール
 */
@Service
public class LeaseNoticeService {

    @Autowired
    LeaseMapper leaseMapper;

    @Autowired
    MailService mailService;

    /**
     * 毎朝9時にリース返却通知を送信
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void sendLeaseReturnNotice() {

        sendNotice("1ヶ月前", leaseMapper.findLeaseReturnAfterOneMonth());
        sendNotice("1週間前", leaseMapper.findLeaseReturnAfterOneWeek());
        sendNotice("1日前", leaseMapper.findLeaseReturnAfterOneDay());
    }

    private void sendNotice(String timing, List<LeaseListDto> leaseList) {

        for (LeaseListDto dto : leaseList) {

            if (dto.getMail() == null || dto.getMail().isBlank()) {
                continue;
            }

            mailService.sendMail(
                    "system@equipment.com",
                    dto.getMail(),
                    "リース返却期限通知",
                    dto.getProductName() + " の返却期限が近づいています。"
            );
        }
    }
}
