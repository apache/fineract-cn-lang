# Apache Fineract CN Lang

## Abstract
Apache Fineract CN is an application framework for digital financial services, a system to support nationwide and cross-national financial transactions and help to level and speed the creation of an inclusive, interconnected digital economy for every nation in the world.

## Prerequisites
### Runtime
Install Java 8 as described at https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html.

## Versioning
The version numbers follow the [Semantic Versioning](http://semver.org/) scheme.

In addition to MAJOR.MINOR.PATCH the following postfixes are used to indicate the development state.

* BUILD-SNAPSHOT - A release currently in development. 
* RELEASE - _General availability_ indicates that this release is the best available version and is recommended for all usage.

The versioning layout is {MAJOR}.{MINOR}.{PATCH}-{INDICATOR}[.{PATCH}]. Only milestones and release candidates can  have patch versions. Some examples:

1.2.3-BUILD-SNAPSHOT  
1.3.5-RELEASE 

## License
See [LICENSE](LICENSE) file.

## Usage

### Generate and print RSA keys

You can use this library to generate and print RSA keys.

You can either generate application-<env>.properties file for Spring applications like this:

`java -cp build/libs/lang-0.1.0-BUILD-SNAPSHOT.jar  org.apache.fineract.cn.lang.security.RsaKeyPairFactory SPRING > application-dev.properties`

Or you can generate them and import as operation system variables (Unix):
```
java -cp build/libs/lang-0.1.0-BUILD-SNAPSHOT.jar  org.apache.fineract.cn.lang.security.RsaKeyPairFactory UNIX > env.vars.txt
source env.vars.txt
echo $PUBLIC_KEY_TIMESTAMP
```
