## TimeBlog
### TimeBlog is a blogging system which carries memories.
    TimeBlog产品定位：针对个人站主、摄影分享、文学作品分享、以及技术极客分享等人群。技术分享，日记功能，推行于类似拾染这种APP的文艺产品。

| Maven模块分类  |作用描述|
|---| ---|
|TimeBlog-core|  系统核心系统块。系统的核心配置、业务核心代码、包括基本的数据操作、所有的实体类、枚举 |
|TimeBlog-admin|后台管理块。 主要为后台模块、针对文章管理、标签管理、分类管理、抓取管理、自动化管理、评论管理、关键字管理、ESO优化等等 |
|TimeBlog-spider|抓取模块。主要功能为抓取CSDN、博客园、简书、小刀娱乐网等网站博客。自动推文到CSDN、博客园等功能(理想功能)|
|TimeBlog-web-skill-blog|前台技术模块(技术模块)。主要功能为博客展示、相册展示、登录评论、EK分词展示相关标签、增加友链等功能(主要做技术展示、技术博客分类)|
|TimeBlog-web-diary-blog|**时光机模块(私密功能)** (个人日记模块，需要密码验证或权限校验)。主要功能为时光机模块。具体功能后期分析。私人模块需要用户标识(MAC地址、用户账户、或者ip地址即用一个唯一标识用户)|
|TimeBlog-mobile-blog|初步推行微信公众号，将博文直接推向微信公众号(后期)|
|TimeBlog-program-admin|移动端微信小程序，功能与admin模块同步，将博文发布的功能实现在手机上。|

 TimeBlog的初步技术选型为SpringBoot+MySql+Nginx+WebSocket+Vue+Template+Tomcat+SpringCloud+Redis+Maven