## 1.项目介绍
  本产品基于地图，旨在打造一款专为年轻人设计更具有青春感的多元化出行工具。用户基于地点上传多媒体信息，进行资源共享，增强人与场景的交互式设计。 
## 2.技术要点
 1. 项目使用ssm框架,布置在阿里云,使用nginx做反向代理和静态文件映射.
 2. 使用token做身份验证,使用redis做登录延期.
 3. 地图算法使用GeoHash算法,将二维坐标转化为一维字符串,方便查询.
 4. 使用谷歌zxing接口制作二维码,实现二维码分享信息.
## 3.项目api
[hi出行api](https://blog.csdn.net/sinat_41905822/article/details/89407121)