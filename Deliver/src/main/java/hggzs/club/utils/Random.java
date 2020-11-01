package hggzs.club.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Random {
    //最大生成9位数
    private static List<Integer> numbers = new ArrayList<>();

    /**
     * 最大生成9位随机数
     * @param n
     * @return
     */
    public static Integer generate(int n){
        if(n>9) return 0;
        Integer res=0, j=1;
        java.util.Random random = new java.util.Random();
        for(int i=0;i<n;i++){
            res+=random.nextInt(9)*j;
            j*=10;
        }
        if(numbers.contains(res)){
            return generate(n);
        }
        numbers.add(res);
        return res;
    }
}
