#from curr_temp import *


#temp() # Gets Current Temperature from api_pract.py

# Calc weekly cost for various appliances/utilities

def t_cost(app, gallons):

        time = 1
        cost = 0
        
        if (app == "light"):
            watts = 60
            cost = 0.12 * ((watts/1000) * time)
            
    
        elif (app == "bath_fan"):
            watts = 30
            cost = 0.12 * ((watts/1000) * time)
            
        
        elif (app == "hvac"):
            watts = 3500
            cost = 0.12 * ((watts/1000) * time)
            
        
        elif (app == "fridge"):
            watts = 150
            cost = 0.12 * ((watts/1000) * time)

        elif (app == "microwave"):
            watts = 1100
            cost = 0.12 * ((watts/1000) * time)
             

        elif (app == "water_heater"):
            watts = 4500
            time = 4 * gallons
            cost = 0.12 * ((watts/1000) * (time/60))

        elif (app == "bath"):
             
             cost = w_cost(30) +  t_cost("water_heater", (30 * .65))
             
        elif (app == "showers"):
             
             cost = w_cost(25) +  t_cost("water_heater", (25 * .65))     

        elif (app == "stove"):
            watts = 3500
            time = ((15 * 5) + (30 * 2)) / 60
            cost = 0.12 * ((watts/1000) * time)
           

        elif (app == "oven"):
            watts = 4000
            time = ((45 * 5) + (60 * 2)) / 60
            cost = 0.12 * ((watts/1000) * time)

        elif (app == "lr_tv"):
            watts = 636
            time = 36
            cost = 0.12 * ((watts/1000) * time)

        elif (app == "br_tv"):
            watts = 100
            time = 18
            cost = 0.12 * ((watts/1000) * time)


        elif (app == "dish"):
            watts = 1800
            time = (45 * 4) / 60
            cost = (0.12 * ((watts/1000) * time)) + w_cost(24) + t_cost("water_heater", 24) 

        elif (app == "clothes_wash"):
            watts = 500
            time = (30 * 4) / 60
            cost = (0.12 * ((watts/1000) * time)) + w_cost(80 * .15) + t_cost("water_heater", (80 * .85))

    
        elif (app == "clothes_dry"):
            watts = 3000
            time = (30 * 4) / 60
            cost = 0.12 * ((watts/1000) * time)
        
        else:
            watts = 0
        
        return cost




# calc cost for cold water 
def w_cost(gallons):

    return (gallons / 748) * 2.52

# Sample Driver 
app = "dish"
gallons = 0 # this is utilized inside t_cost(..) function for hot_water heater cost calculation
print("\nCost for %s: $" % app, t_cost(app, gallons), "\n")



    

