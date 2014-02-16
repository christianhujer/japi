vpath %.c src/solaris/native/sun/security/smartcardio/
all: libriedquatsmartcardio.so

override CPPFLAGS+=\
    -I.\
    -I/usr/include/PCSC/\
    -I/usr/lib64/jvm/java-1.7.0/include/\
    -I/home/christian/openjdk/jdk/src/share/native/common/\
    -I/home/christian/openjdk/jdk/src/solaris/native/common/\

override CFLAGS+=-fPIC

libriedquatsmartcardio.so: pcsc_md.o
	$(CC) -shared -Wl,-soname,libriedquatsmartcardio.so -o $@ pcsc_md.o -lc

clean:
	rm libriedquatsmartcardio.so pcsc_md.o
