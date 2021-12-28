package top.camsyn.store.request.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.request.Label;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.request.mapper.LabelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LabelService extends SuperServiceImpl<LabelMapper, Label> {


    public Label getByName(String name) {
        return lambdaQuery().eq(Label::getLabelName, name).one();
    }

    public void updateFrequency(Collection<String> labels, boolean isInc){
        if (isInc){
            baseMapper.increaseFreq(labels);
        }else {
            baseMapper.declineFreq(labels);
        }
    }

    public List<Label> queryOrCreate(Collection<String> labelNames) {
        return labelNames.stream().map(
                name -> {
                    Label label = getByName(name);
                    if (label == null) {
                        label = new Label();
                        save(label);
                    }
                    return label;
                }
        ).collect(Collectors.toList());
    }
    public List<Label> getLabelsByFreqOrder(IPage<Label> iPage) {
        return page(iPage, new LambdaQueryWrapper<Label>().orderByDesc(Label::getFrequency)).getRecords();
    }


}
