import requests

# Get External Temperature
def temp():
    
    url = "https://api.open-meteo.com/v1/forecast?latitude=33.52&longitude=-86.80&hourly=temperature_2m&daily=temperature_2m_max,temperature_2m_min&timezone=auto&temperature_unit=fahrenheit&current_weather=true"

    response = requests.request("GET", url)
    
    curr_w = response.json().get('current_weather')

    #hourly = response.json().get('hourly')

    #daily = response.json().get('daily')

    #hourly_temps = hourly.get('temperature_2m')

    #daily_max_temps = daily.get('temperature_2m_max')

    #ls = []

    #print("\nCurrent Weather: \n", curr_w)

    #print("\nHourly Temp: \n", hourly.get('temperature_2m'))

    #print("\nMax Daily Temp: ", daily.get('temperature_2m_max'), '\n')

    '''
    for t in hourly.get('temperature_2m'):
        ls.append(t)

    temp_ls = ls[:24]
    print("\n24 Hour Temperature Forecast: \n", temp_ls)
    '''

    return curr_w.get('temperature')

# Display Results
print("\nCurrent Temperature: ", temp(), "F\n")

