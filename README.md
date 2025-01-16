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

## Entity

[BaseEntity](src/main/java/com/example/demo/model/BaseEntity.java)  
[NamedEntity](src/main/java/com/example/demo/model/NamedEntity.java)  
[Dictionary](src/main/java/com/example/demo/model/Dictionary.java)  
[Ingredient](src/main/java/com/example/demo/model/Ingredient.java)

## DTO

[IngredientBodyDTO](src/main/java/com/example/demo/dto/ingredient/IngredientBodyDTO.java)
[AuthRequestDTO](src/main/java/com/example/demo/dto/auth/AuthRequestDTO.java)

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

## @JsonFormat

[BaseEntity](src/main/java/com/example/demo/model/BaseEntity.java) [RecipeProjection](src/main/java/com/example/demo/dto/recipe/RecipeProjection.java) [RecipeSummaryDTO](src/main/java/com/example/demo/dto/recipe/RecipeSummaryDTO.java)

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
GET /recipes/list/search?name=面条&description=辣&ingredients=肉丝 青椒
```

## Redis 缓存

- [pom.xml](./pom.xml)中添加`spring-boot-starter-data-redis`和`spring-boot-starter-cache`
- [application-redis.properties](src/main/resources/application-redis.properties)
- 在主类或配置类上添加 `@EnableCaching` 注解 : [RedisConfig.java](src/main/java/com/example/demo/config/RedisConfig.java)
- `@Cacheable` `@CachePut` `@CacheEvict` : [DictionaryService.java](src/main/java/com/example/demo/service/DictionaryServiceImpl.java)
- 默认情况下，Redis 使用二进制序列化，但你可以配置 JSON 序列化以提高可读性 : [RedisConfig.java](src/main/java/com/example/demo/config/RedisConfig.java) : 添加`RedisTemplate`和`CacheManager`的配置，并且使用相同的序列化方式

## Docker

- [application-mysql.properties](src/main/resources/application-mysql.properties) `${DB_HOST:localhost}`
- [application-redis.properties](src/main/resources/application-redis.properties) `${DB_HOST:localhost}`
- [Dockerfile](./Dockerfile)
- [docker-compose.yml](./docker-compose.yml)
- [build.sh](./build.sh)

## Transaction

- [RecipeService.java](src/main/java/com/example/demo/service/RecipeServiceImpl.java)
  - `createRecipeWithIngredients`
  - `editRecipeWithIngredients`

## lombok

[User.java](src/main/java/com/example/demo/model/User.java)
[RecipeIngredientId.java](src/main/java/com/example/demo/model/RecipeIngredientId.java)

## Spring Security & JWT (注册登录)

- [pom.xml](/pom.xml) 添加 spring-boot-starter-security
- 实现 `UserDetailsService` : [CustomUserDetailsService](src/main/java/com/example/demo/service/CustomUserDetailsService.java)
- 自定义 JWT 过滤器 : [JwtAuthenticationFilter](src/main/java/com/example/demo/util/JwtAuthenticationFilter.java)
- 通过 `SecurityFilterChain` 配置 `Spring Security` : [SecurityConfig](src/main/java/com/example/demo/config/SecurityConfig.java)
- JWT 工具类 : [JwtUtils](src/main/java/com/example/demo/util/JwtUtils.java)
- [AuthService](src/main/java/com/example/demo/service/AuthServiceImpl.java)
- [AuthController](src/main/java/com/example/demo/controller/AuthController.java)

## 密码加密存储：BCrypt

- [SecurityConfig](src/main/java/com/example/demo/config/SecurityConfig.java)
  - `passwordEncoder`
- [AuthService](src/main/java/com/example/demo/service/AuthServiceImpl.java)
  - `register`

## Kaptcha

- [KaptchaConfig.java](src/main/java/com/example/demo/config/KaptchaConfig.java)
- [CaptchaController.java](src/main/java/com/example/demo/controller/CaptchaController.java)
- [AuthController](src/main/java/com/example/demo/controller/AuthController.java)
