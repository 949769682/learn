package hggzs.club.componet;

import hggzs.club.entity.FileInfo;
import hggzs.club.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Component
public class TimeDelete {
    @Autowired
    private FileInfoRepository fir;

    /**
     * 定时清理文件和数据库
     * @throws UnsupportedEncodingException
     */
    @Scheduled(fixedRate = 1000*60*60)
    public void deleteFile() throws UnsupportedEncodingException {
        String path = this.getClass().getResource("/").getPath();
        path = URLDecoder.decode(path, "utf-8").replaceFirst("/", "")+"static/files/";
        List<FileInfo> all = fir.findAll();
        for(FileInfo info:all){
            Date now = new Date();
            //判断文件是否过期或下载次数用尽
            if(info.getExTime().getTime()-now.getTime()<0||info.getDlNumber()<1){
                File file = new File(path+info.getName());
                if(file.exists()) file.delete();
                fir.delete(info);
            }
        }
    }
}
