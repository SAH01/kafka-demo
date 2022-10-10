import pymysql
def get_conn():
    """
    获取连接和游标
    :return:
    """
    conn = pymysql.connect(host="127.0.0.1",
                           user="root",
                           password="reliable",
                           db="spark",
                           charset="utf8")
    cursor = conn.cursor()
    return conn, cursor


def close_conn(conn, cursor):
    """
    关闭连接和游标
    :param conn:
    :param cursor:
    :return:
    """
    if cursor:
        cursor.close()
    if conn:
        conn.close()


# query
def query(sql, *args):
    """
    通用封装查询
    :param sql:
    :param args:
    :return:返回查询结果 （（），（））
    """
    conn, cursor = get_conn()
    print(sql)
    cursor.execute(sql)
    res = cursor.fetchall()
    close_conn(conn, cursor)
    return res


def dynamic_bar():
    # 获取数据库连接
    conn, cursor = get_conn()
    if (conn != None):
        print("数据库连接成功！")
    typenumsql = "select * from content_num  order by type DESC limit 5;"
    detail_sql = ""
    res_title = query(typenumsql)
    type_num = []  # 存储类别+数量
    for item1 in res_title:
        type_num.append(item1)
    return type_num