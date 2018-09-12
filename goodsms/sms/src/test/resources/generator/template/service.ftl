package ${basePackage}.service;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.core.Service;


/**
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}> {
      void ${modelNameLowerCamel}Add(${modelNameUpperCamel} ${modelNameLowerCamel},String userId);
      void ${modelNameLowerCamel}Update(${modelNameUpperCamel} ${modelNameLowerCamel},String userId);
}
