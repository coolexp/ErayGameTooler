1,安装MinGW,并设置环境变量C:\Program Files (x86)\mingw-w64\i686-4.9.1-posix-dwarf-rt_v3-rev0\mingw32\bin，Path追加
2,设置xx:\ANT\bin的环境变量，Path追加
3,设置protoc.exe所在的目录为环境变量 Path追加
注：修改Package.bat
rem 第一个变量为是否输出数据，第二变量为是否输出Class文件，第三个是Excel表格所在路径 第四个是当前的Package.bat的所在目录，第五个是包名，用默认，第六个是，产生后的Class类文件目录             
java -jar ErayGameTooler.jar 1 1 D:\data d:\worksp ERAY_PROTOBUF D:\classfolder