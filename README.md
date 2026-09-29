# Lab04 - Нэгжийн тест JUnit 5

- **Нэр:** Номунзаяа
- **Оюутны код:** B232270804

## Орчин

`java -version`:
~~~
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)
~~~

`mvn -version`:
~~~
[1mApache Maven 3.9.12[m
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "6.18.33.2-microsoft-standard-wsl2", arch: "amd64", family: "unix"
~~~

## Дүгнэлт

Нийт 15 тестийн метод бичсэн бөгөөд 4 нь @ParameterizedTest тул mvn test дээр Tests run: 39, Failures: 0 гарсан. Мутаци болгож letterGrade доторх score >= 90-ийг score > 90 болгоход 2 тест унасан. Эдгээр нь ninetyIsExactlyA болон letterGradeBoundaries-ийн 90,A тохиолдол бөгөөд хоёулаа expected: A but was: B гэсэн алдаа өгсөн. Мутацийн гаралтад Tests run: 39, Failures: 2 гарч, үлдсэн 37 тест ногоон хэвээр байсан. Хамгийн сонирхолтой нь нэг тэмдэг өөрчлөгдсөнөөс болж яг 90 оноотой оюутан A биш B авдаг хилийн утгын алдаа байсан. 85 эсвэл 95 гэх мэт ердийн оноогоор шалгавал энэ алдаа мэдэгдэхгүй, зөвхөн хилийн утга дээр л илэрнэ. Иймээс 90, 89.99 зэрэг хил дээрх утгуудыг тусад нь шалгах нь чухал гэдгийг ойлгосон. Мутацийг буцаасны дараа 39 тест дахин ногоон болсон.
