package ${basePackage}.service.impl;


import com.nice.good.utils.TimeStampUtils;
import ${basePackage}.dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    @Override
    public  void ${modelNameLowerCamel}Add(${modelNameUpperCamel} ${modelNameLowerCamel},String userId){


        ${modelNameLowerCamel}.setCreateId(userId);
        ${modelNameLowerCamel}.setModifyId(userId);
        ${modelNameLowerCamel}.setCreateDate(TimeStampUtils.getTimeStamp());
        ${modelNameLowerCamel}.setModifyDate(TimeStampUtils.getTimeStamp());

        ${modelNameLowerCamel}Mapper.insert(${modelNameLowerCamel});

    }


   @Override
   public void  ${modelNameLowerCamel}Update(${modelNameUpperCamel} ${modelNameLowerCamel},String userId){

        ${modelNameLowerCamel}.setModifyId(userId);
        ${modelNameLowerCamel}.setModifyDate(TimeStampUtils.getTimeStamp());
        ${modelNameLowerCamel}Mapper.updateByPrimaryKey(${modelNameLowerCamel});
   }

}
