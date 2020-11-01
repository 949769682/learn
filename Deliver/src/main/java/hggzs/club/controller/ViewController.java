package hggzs.club.controller;


import hggzs.club.entity.FileInfo;
import hggzs.club.repository.FileInfoRepository;
import hggzs.club.utils.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;


@Controller
public class ViewController {
    @Autowired
    private FileInfoRepository fir;
    private final String path;

    ViewController() throws UnsupportedEncodingException {
        String path = this.getClass().getResource("/").getPath();
        this.path = URLDecoder.decode(path, "utf-8").replaceFirst("/", "")+"static/files/";
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {
        String path = this.getClass().getResource("/").getPath();

        return URLDecoder.decode(path, "utf-8").replaceFirst("/", "");
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file.isEmpty()){
            return "上传失败";
        }
        Date date = new Date();
        FileInfo fileInfo = new FileInfo();
        Integer id = Random.generate(6);
        fileInfo.setId(id);
        fileInfo.setName(file.getOriginalFilename());    //写入文件名
        fileInfo.setPassword(request.getParameter("password"));  //写入密码
        fileInfo.setDlNumber(Integer.parseInt(request.getParameter("dlNumber"))); //写入下载次数
        fileInfo.setUpTime(date);          //写入上传时间
        Integer time = Integer.parseInt(request.getParameter("time"));
        fileInfo.setTime(time);     //写入保存时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, time);
        date = calendar.getTime();
        fileInfo.setExTime(date);   //写入过期时间
        fileInfo.setSize(file.getSize());
        try {
            File file1 = new File(path+fileInfo.getName());
            if (!file1.exists()) {
                System.out.println(file1.mkdirs());
            }
            file.transferTo(file1);
            fir.save(fileInfo);
            return id.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    @GetMapping("/check")
    @ResponseBody
    public String check(@RequestParam("pwd") String pwd,
                        @RequestParam("vCode") String vCode,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(vCode);
        FileInfo fileInfo = fir.findById(id).orElse(null);
        if (fileInfo==null){
            return "取件码错误";
        }
        if (fileInfo.getPassword().equals("")||fileInfo.getPassword().equals(pwd)){
            if(fileInfo.getDlNumber()<=0){ return "此文件已删除";}
            request.setAttribute("fileInfo", fileInfo);
            request.getRequestDispatcher("/download").forward(request, response);
        }
        return "密码错误";

    }
    @GetMapping("/download")
    public ResponseEntity<Object> download(HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException {
       FileInfo fileInfo = (FileInfo) request.getAttribute("fileInfo");
        File file = new File(path+fileInfo.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add ( "Content-Disposition",String.format("attachment;filename=\"%s",fileInfo.getName()));
        fileInfo.setDlNumber(fileInfo.getDlNumber()-1);
        fir.save(fileInfo);  //更新数据库中此文件的剩余下载次数
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType ( "application/txt" ))
                .body(resource);
    }
}
