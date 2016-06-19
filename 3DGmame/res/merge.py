# -*- coding : utf-8 -*-

def main():
    v = []
    t = []
    n = []
    f = []
    fout = open("temple.obj", "w")
    for i in range(24):
        fin = open("t" + str(i+1)+".obj")
        for line in fin.readlines():
            if line[0] == "v" and line[1] == " ":
                v.append(line)
            if line[0] == "v" and line[1] == "t":
                t.append(line)
            if line[0] == "v" and line[1] == "n":
                n.append(line)
            if line[0] == "f":
                f.append(line)
    for line in v:
        fout.write(line)
    for line in t:
        fout.write(line)
    for line in n:
        fout.write(line)
    for line in f:
        if line[-1] != "\n":
            line += "\n"
        fout.write(line)


if __name__=="__main__":
    main()