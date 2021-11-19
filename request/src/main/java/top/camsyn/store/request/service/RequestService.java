package top.camsyn.store.request.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.mapper.AccountMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.request.mapper.RequestMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService extends SuperServiceImpl<RequestMapper, Request>  {

    public List<Request> getRequestPageByLabel(String labelName, int page, int pageSize){
        QueryWrapper<Request> query = new QueryWrapper<Request>()
                .eq("label_name", labelName)
                .orderByDesc("update_time");
       return this.baseMapper.pageOfRequestByLabel(new Page<>(page, pageSize), query);
    }
    public List<Request> getRequestRandomlyByLabel(String labelName, int count){
        QueryWrapper<Request> query = new QueryWrapper<Request>()
                .eq("label_name", labelName)
                .orderByAsc("rand()").last("limit "+count);
       return this.baseMapper.getRequestByLabel(query);
    }
    public List<Request> getRequestRandomly(int count){
       return query().orderByAsc("rand()").last("limit "+count).list();
    }
    public List<Request> getRequestPage(int page, int pageSize){
       return page(new Page<>(page,pageSize)).getRecords();
    }

    public List<Request> search(int page, int pageSize, String pattern){

        return new ArrayList<>();
    }


}
