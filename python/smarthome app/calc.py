#1st month - 30 days 2+2+2+2 = 8 weekend days
#2nd - 31 day 2+2+2+2 = 8 weekend days
#3d - 30 days 2+2+2+2+2 = 10 weekend days
import random
import matplotlib.pyplot as plt 
import psycopg2


set = 70
gln = 2.52/748 #price per 1 gallon
kw = 0.12/60 #price per 1 hour
w = [1, 1, 1, 1, 1, 1, 10, 10, 1, 1, 1, 1, 1, 1, 1, 10, 10, 10, 10, 10, 1, 1, 1, 1] #24 hours weights for randomized opening of doors and windows
res = []
def grand(mu, sigma):
    return round(random.gauss(mu, sigma))

def weekend(month):

    ltv = 8 * 60 * 0.636 * kw 
    btv = 4* 60 * 0.1 * kw 
    mw = 1.1 * 30 * kw
    shower = 3 * 25 * gln
    bath  = 3 * 30 * gln
    fan = (3 * 30 + 3 * 25) * 0.03 * kw
    dishw_el = 1.8 * 2 * 45 * kw
    dishw_wat = 2 * 6 * gln
    dryer = 3 * 2 * 30 * kw
    clothes_el = 0.5 * 30 * 2 * kw
    clothes_wat = 2 * 20 * gln
    refr = 0.15 * 24 * 60 * kw
    stove = 3.5 * 30 * kw
    oven = 4 * 60 * kw
    heater = 4.5 * 4 * (3 * 25 * 0.65 + 3 * 30 * 0.65 + 2*6 + 2*20*0.85) * kw                 
    # randomized number of hrs lamps were on in 1.bedrooms 2. bathrooms 3. living room 4. kitchen
    lamps = 0.06 * 60 * kw * (3*3* grand(3, 1) + 2*grand(2, 1) + 3*grand(3, 2) + grand(2,4))   
    hvac = ac(month, 0) * kw
    total_wat = (shower + bath + dishw_wat + clothes_wat) * random.uniform(1, 1.5)
    total_el = ltv+btv+mw+fan+dishw_el+dryer+clothes_el+refr+stove+oven+heater+lamps+hvac

    return round(total_wat, 2), round(total_el, 2)

def weekday(month):
    
    ltv = 4 * 60 * 0.636 * kw 
    btv = 2* 60 * 0.1 * kw 
    mw = 1.1 * 20 * kw
    shower = 2 * 25 * gln
    bath  = 2 * 30 * gln
    fan = (2 * 30 + 2 * 25) * 0.03 * kw
    refr = 0.15 * 24 * 60 * kw
    stove = 3.5 * 15 * kw
    oven = 4 * 45 * kw
    heater = 4.5 * 4 * (2 * 25 * 0.65 + 2 * 30 * 0.65) * kw                 
    # randomized number of hrs lamps were on in 1.bedrooms 2. bathrooms 3. living room 4. kitchen
    lamps = 0.06 * 60 * kw * (3*3* grand(2, 1) + 2*grand(1, 1) + 3*grand(2, 2) + grand(2,4))   
    hvac = ac(month, 1) * kw
    total_wat = (shower + bath) * random.uniform(1, 1.5)
    total_el = ltv+btv+mw+fan+refr+stove+oven+heater+lamps+hvac

    return round(total_wat, 2), round(total_el, 2)


def Weather(month):
    if month == 1: #cold month
        return grand(49, 10)
    elif month == 2: #neutral month
        return grand(58, 10)
    elif month == 3: #hot month
        return grand(80, 13)
    
    #weekend = 0 weekday = 1
def ac (month, day):
    nums = []
    doors = 16 + random.randint(2,6)
    
    if month == 1:
        windows_number = round(random.triangular(0, 8, 1))
    elif month == 2:
        windows_number = round(random.triangular(0, 8, 8))
    elif month == 3:
        windows_number = round(random.triangular(0, 8, 3))
    if day == 0:
        windows_number *= random.randint(1,3)
        doors = 32 + random.randint(0,6)
    hours_when_doors_opened = random.choices(range(24), weights = w,k = doors)

    for i in range(24):
       temp = Weather(month)
       nums.append(temp)
    hours_windows = random.choices(range(24), weights =w, k = windows_number)
    total = 0

    for i in range(len(nums)):
        inside = 70
        dif = abs(nums[i] - inside)
        if dif > 2:
            door = 0.5 * hours_when_doors_opened.count(i) # minutes doors were opened in that hour
            windows = random.randint(5, 15) * hours_windows.count(i) # minutes windows were opened in that hour
            house_closed = 60 - door - windows
            if house_closed > 0:
                inside = set + (door * 0.2 * dif / 5) + (windows * 0.1 * dif / 5) + (house_closed * 0.2 * dif / 60)
            else:
                inside = set + (door * 0.2 * dif / 5) + (windows * 0.1 * dif / 5)
            total += abs(set - inside)
    return round(total)

def driver():
    weekends = [6, 7, 13, 14, 20, 21, 27, 28, 4, 5, 11, 12, 18, 19, 25, 26, 1, 2, 8, 9, 15, 16, 22, 23, 29, 30]
    for i in range(1, 92):
        if i < 30:
            month = 1
        elif 30 < i < 62:
            month = 2
        else:
            month = 3
        if i in weekends:
            wat, el = weekend(month)
            res.append((i, wat, el))
        else:
            wat, el = weekday(month)
            res.append((i, wat, el))
    return res

driver()
# print(res)

# plt.hist(nums, bins = 10) 
# plt.show()
con = psycopg2.connect(
database='Team2DB',
user='Team2',
password='team2',
host='138.26.48.83',
port= '5432'
)
con.autocommit = True
cur = con.cursor()
sql0 = '''CREATE TABLE IF NOT EXISTS costs (
        day INT NOT NULL,
        water_cost FLOAT NOT NULL,
        electr_cost FLOAT NOT NULL
);'''
cur.execute(sql0)
for i in res:
    cur.execute(''' INSERT INTO costs (day, water_cost, electr_cost) VALUES (%s, %s, %s);''', i)

cur.execute('''SELECT * FROM costs;''')
print(cur.fetchall())

cur.execute(''' DELETE FROM costs''')
cur.close()
con.close()