# Spring-boot-template

## [Swagger-api-docs](http://localhost:8080/swagger-ui/index.html)

## 目录结构

```
├── model           实体类
├── dto             数据传输对象
├── repository      数据层
├── service         业务层
├── controller      API层
├── config          JPA审计，全局拦截(advice)
├── util            工具类
└── exception       异常处理
```

## RESTful Web Service
[HelloController.java](src/main/java/com/example/demo/controller/HelloController.java)

## JPA
[IngredientRepository.java](src/main/java/com/example/demo/repository/IngredientRepository.java)

## MySQL

- [pom.xml](./pom.xml) : `mysql-connector-j` `spring-boot-docker-compose`
- [application-mysql](./src/main/resources/application-mysql.properties)
- [compose.yml](./compose.yml)

## JPA 审计

- 引入 Spring Data JPA 的 @EnableJpaAuditing 以启用自动时间管理，例：[JpaConfig.java](src/main/java/com/example/demo/config/JpaConfig.java)
- `@CreatedDate` `@LastModifiedDate`，例：[BaseEntity.java](src/main/java/com/example/demo/model/BaseEntity.java)

## 多对一

多个当前实体对象[Ingredient](src/main/java/com/example/demo/model/Ingredient.java)可以对应一个关联的目标实体对象[Dictionary](src/main/java/com/example/demo/model/Dictionary.java)

## 多对多

[Recipe](src/main/java/com/example/demo/model/Recipe.java)(菜谱)和[Ingredient](src/main/java/com/example/demo/model/Ingredient.java)(食材)是多对多，创建中间实体[RecipeIngredient](src/main/java/com/example/demo/model/RecipeIngredient.java)

## @JsonIgnore

[Ingredient.java](src/main/java/com/example/demo/model/Ingredient.java)

## 投影 Projection

[RecipeRepository.java](src/main/java/com/example/demo/repository/RecipeRepository.java)

## JPQL

[RecipeRepository.java](src/main/java/com/example/demo/repository/RecipeRepository.java)

## NativeSQL

[RecipeRepository.java](src/main/java/com/example/demo/repository/RecipeRepository.java)

## 分页查询

- 在 Spring Data JPA 的 JpaRepository 中使用 Page 对象实现分页查询，例如 [RecipeRepository.java](src/main/java/com/example/demo/repository/RecipeRepository.java)
- 在 Service 层实现分页查询，例如：[RecipeService.java](src/main/java/com/example/demo/service/RecipeServiceImpl.java)
- 在 Controller 层处理分页请求，例如：[RecipeController.java](src/main/java/com/example/demo/controller/RecipeController.java)

## 统一响应格式

[ApiResponse.java](src/main/java/com/example/demo/dto/ApiResponse.java)

## 分页响应格式

[PageResponse.java](src/main/java/com/example/demo/dto/PageResponse.java)

## 异常处理

- 创建[ResourceNotFoundException](src/main/java/com/example/demo/exception/ResourceNotFoundException.java)
- 在`Service` 或 `Controller`中 throw`ResourceNotFoundException`，例如[DictionaryServiceImpl](src/main/java/com/example/demo/service/DictionaryServiceImpl.java)
- 创建 the Global Exception Handler：[GlobalExceptionHandler](src/main/java/com/example/demo/exception/GlobalExceptionHandler.java)

## 复杂动态查询

#### 根据名称和单位动态查询食材

- 新建[IngredientSpecification.java](src/main/java/com/example/demo/repository/IngredientSpecification.java)，用于动态构建查询条件
- 修改[IngredientRepository.java](src/main/java/com/example/demo/repository/IngredientRepository.java)，继承 `JpaSpecificationExecutor`
- 在 [IngredientService.java](src/main/java/com/example/demo/service/IngredientServiceImpl.java) 中调用动态查询
- 在 [IngredientController.java](src/main/java/com/example/demo/controller/IngredientController.java) 中添加接口

```
GET /ingredients/search?name=鸡蛋&unit=1
```

#### 基于菜谱名、描述和多个食材名的动态查询

- 新建[RecipeSpecification.java](src/main/java/com/example/demo/repository/RecipeSpecification.java)，用于动态构建查询条件
- 修改[RecipeRepository.java](src/main/java/com/example/demo/repository/RecipeRepository.java)，继承 `JpaSpecificationExecutor`
- 在 [RecipeService.java](src/main/java/com/example/demo/service/RecipeServiceImpl.java) 中调用动态查询
- 在 [RecipeController.java](src/main/java/com/example/demo/controller/RecipeController.java) 中添加接口

```
GET /recipes/list/search?name=面条&description=辣&ingredients=肉丝,青椒
```
