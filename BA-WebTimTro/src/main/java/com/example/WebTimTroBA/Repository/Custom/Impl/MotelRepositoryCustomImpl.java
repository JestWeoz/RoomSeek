package com.example.WebTimTroBA.Repository.Custom.Impl;

import com.example.WebTimTroBA.Model.Entity.MotelEntity;
import com.example.WebTimTroBA.Repository.Custom.MotelRepositoryCustom;
import com.example.WebTimTroBA.Model.Search.MotelSearchBuilder;
import com.example.WebTimTroBA.Utils.NumberUtils;
import com.example.WebTimTroBA.Utils.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;

@Repository
public class MotelRepositoryCustomImpl implements MotelRepositoryCustom {

    public void queryNormal(StringBuilder sql, MotelSearchBuilder motelSearchBuilder){
        sql.append(" where 1 = 1 ");
        try{
            Field[] fields = MotelSearchBuilder.class.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(motelSearchBuilder);
                if(!fieldName.startsWith("area") && !fieldName.startsWith("price") && !fieldName.startsWith("key")){
                    if(NumberUtils.isNumber(value)) {
                        sql.append(" and m." + fieldName + " = " + value + " ");
                    }
                    else if(StringUtils.isString(value)){
                        sql.append(" and m." + fieldName + " like '%" + value +"%' ");
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void keywordQuery(StringBuilder sql, MotelSearchBuilder motelSearchBuilder){
        String keyword = motelSearchBuilder.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" and (m.title like '%" + keyword + "%' ");
            sql.append(" or m.ward like '%" + keyword + "%' ");
            sql.append(" or m.street like '%" + keyword + "%' ");
            sql.append(" or m.interior like '%" + keyword + "%' ");
            sql.append(" or m.district like '%" + keyword + "%' ");
            sql.append(" or m.province like '%" + keyword + "%') ");
        }

    }
    public void querySpecial(StringBuilder sql, MotelSearchBuilder motelSearchBuilder){
        Integer priceFrom = motelSearchBuilder.getPriceFrom();
        Integer priceTo = motelSearchBuilder.getPriceTo();
        Integer areaFrom = motelSearchBuilder.getAreaFrom();
        Integer areaTo = motelSearchBuilder.getAreaTo();

        if(priceFrom != null){
            sql.append(" and m.price >= " + motelSearchBuilder.getPriceFrom() + " ");
        }

        if(priceTo != null){
            sql.append(" and m.price <= " + motelSearchBuilder.getPriceTo() + " ");
        }

        if(areaFrom != null){
            sql.append(" and m.area >= " + motelSearchBuilder.getAreaFrom() + " ");
        }

        if(areaTo != null){
            sql.append(" and m.area <= " + motelSearchBuilder.getAreaTo() + " ");
        }
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MotelEntity> searchByMotelSearchBuilder(MotelSearchBuilder motelSearchBuilder) {
        StringBuilder sql = new StringBuilder(" select m.* from motel m ");
        queryNormal(sql, motelSearchBuilder);
        querySpecial(sql, motelSearchBuilder);
        keywordQuery(sql, motelSearchBuilder);
        sql.append(" and m.status = 1");
        Query query = entityManager.createNativeQuery(sql.toString(), MotelEntity.class);
        return query.getResultList();
    }
}
