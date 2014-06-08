vpath %.c src/solaris/native/sun/security/smartcardio/
all: libriedquatsmartcardio.so

JAVA_INCDIR:=/usr/lib64/jvm/java-1.7.0/include/
OPENJDK_SRC_PATH:=/home/christian/openjdk/jdk/src/

override CPPFLAGS+=\
    -I.\
    -I/usr/include/PCSC/\
    -I$(JAVA_INCDIR)\
    -I$(OPENJDK_SRC_PATH)share/native/common/\
    -I$(OPENJDK_SRC_PATH)solaris/native/common/\

override CFLAGS+=-fPIC

libriedquatsmartcardio.so: pcsc_md.o
	$(CC) -shared -Wl,-soname,libriedquatsmartcardio.so -o $@ pcsc_md.o -lc

clean:
	rm libriedquatsmartcardio.so pcsc_md.o
