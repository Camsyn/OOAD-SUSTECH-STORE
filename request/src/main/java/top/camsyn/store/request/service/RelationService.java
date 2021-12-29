//package top.camsyn.store.request.service;
//
//import org.springframework.stereotype.Service;
//import top.camsyn.store.commons.entity.request.Label;
//import top.camsyn.store.commons.entity.request.LabelRequestRelation;
//import top.camsyn.store.commons.entity.request.Request;
//import top.camsyn.store.commons.service.impl.SuperServiceImpl;
//import top.camsyn.store.request.mapper.RelationMapper;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class RelationService extends SuperServiceImpl<RelationMapper, LabelRequestRelation> {
//
////    public boolean bindRequestAndLabel(List<Label> labels, Request request) {
////        List<LabelRequestRelation> relations = labels.stream().map(label -> new LabelRequestRelation(label.getId(), request.getId())).collect(Collectors.toList());
////        return saveBatch(relations);
////    }
////    public boolean unbindRequestAndLabel(List<Label> labels, Request request) {
////        List<LabelRequestRelation> relations = labels.stream().map(label -> new LabelRequestRelation(label.getId(), request.getId())).collect(Collectors.toList());
////        this.baseMapper.removeBatch(labels.stream().map(Label::getId).collect(Collectors.toList()), request.getId());
////        return saveBatch(relations);
////    }
//}
