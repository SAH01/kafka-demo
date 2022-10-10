from flask import Flask, render_template

import spark_sql

app = Flask(__name__)


@app.route('/')
def hello_world():
    return render_template("line.html")
#获取 动态 柱状图数据
@app.route('/dynamic_bar')
def dynamic_bar():
    res_list=spark_sql.dynamic_bar()
    my_list=[]
    list_0=[]
    list_1=[]
    for item in res_list:
        list_0.append(item[0])
        list_1.append(item[1])
    my_list.append(list_0)
    my_list.append(list_1)
    return {"data":my_list}

if __name__ == '__main__':
    app.run()
