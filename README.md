# LFSR and Encryption

Linear feedback shift register, based on the assignment given in the Princeton course COS 126 and the Upenn course CIS 110 || Java 实现的线性反馈移位寄存器，对图片进行编码和解码

```
.
│   .gitignore
│   LICENSE
│   pipe.png # test iamge for encryption
│   surprise.png # encrypted picture
│   README.md
├───res
│       guesschain.txt # save guessing records
│       keychain.txt # save generated key
└───src
        BreakPhotoMagic.java # try all possible seeds and all possible taps and finds the picture
        LFSR.java # simulates the operation of an LFSR
        PhotoMagic.java # encrypt picture
        PhotoMagicDeluxe.java # could take an alphanumeric (letters and numbers) password
        Picture.java # given class to deal eith picture
```

## 1 Overview
- This is based on the assignment given in
  - [the Princeton course COS 126](https://www.cs.princeton.edu/courses/archive/fall10/cos126/assignments/lfsr.html)
  - [the Upenn course CIS 110](https://www.cis.upenn.edu/~cis110/13sp/hw/hw04/lfsr.shtml)
- jdk: openjdk8-redhat

## 2 Requirements
- Write a program that can encrypt pictures.
- In order to encrypt the picture, we need a source of bits which look random to any attacker (pseudo-random).
- We will generate these using a linear feedback shift register (LFSR) in LFSR.java.

## 3 Reference
- https://introcs.cs.princeton.edu/java/stdlib/javadoc/Picture.html
- https://www.cs.princeton.edu/courses/archive/fall10/cos126/assignments/lfsr.html
- https://www.cis.upenn.edu/~cis110/13sp/hw/hw04/lfsr.shtml

## 4 Challenge
The challenge listed in the assignment:
> Tell me any ideas you have for decrypting a picture faster.
>
> a) In this setting, you must think of decryption methods assuming I hand you an encrypted picture and nothing else.
>
> b) A second setting that you can consider is what if I give you (1) an encrypted picture, and (2) access to the pre-initialized LFSR as a black-box. Black-box means that you can ask the LFSR for random bits and it will give them to you. But, you cannot see inside of it. So, you cannot just look and see a variable like "seed = xxx".


I don't really think I have a good idea about the two listed settings. But I come up with some ideas for just accelerating decrypting a picture.(not sure, just visions)

- Optimize the code structure. Reduce the redundant code and unnecessary work, for example, we can simplify the definition of variables and methods.

- Improve decrypting algorithm. Considering general situation, initial value of the seed: `String smallestStrSeed `, more likely started from `"1111"` than `"0"`. Thus we can reduce unnecessary work form `"0"` to `"1111"`. Similarly, if we can figure out the stopping condition of loops, we can make the program early-stopping to reduce extra calculations.

- Improve data structures. Image processing is often reminiscent of matrix and other advanced math operations. Maybe we can use advanced data structures to greatly simplify the processing flow.

- Apply image preprocessing methods. To determine whether a picture is garbage or not, some common image processing methods can also be applied, such as convolution operation to extract the key features of the picture. And perhaps we do not need to calculate the value of each pixel, but only need to calculate the value of each area composed of a certain number of pixels.

- Parallel computing. This is a general method. Considering that there are multiple loops in the progra, we can try to parallelize the serial program with smaller units like kernels. And make full use of hardware such as GPUs to reduce overall time.
