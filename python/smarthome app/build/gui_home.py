
# This file was generated by the Tkinter Designer by Parth Jadhav
# https://github.com/ParthJadhav/Tkinter-Designer


from pathlib import Path
import math
from calc import *
from curr_temp import *
# from tkinter import *
# Explicit imports to satisfy Flake8
from tkinter import Tk, Canvas, Entry, Text, Button, PhotoImage, StringVar, Message
#from tkinter import *


OUTPUT_PATH = Path(__file__).parent
ASSETS_PATH = OUTPUT_PATH / Path(r"./assets/frame3")


def relative_to_assets(path: str) -> Path:
    return ASSETS_PATH / Path(path)

# Menu command
def get_menu():
    from gui_menu import frame1
    frame1()
    
# Maintenance command
def get_maintenance():
    from gui_maintenance import frame2
    frame2()

# Usage command
def get_usage():
    from gui_usage import frame3
    frame3()

def frame4():       
    window = Tk()

    window.geometry("1014x620")
    window.configure(bg = "#5C66C4")
    window.title("Home")

    canvas = Canvas(
        window,
        bg = "#5C66C4",
        height = 620,
        width = 1014,
        bd = 0,
        highlightthickness = 0,
        relief = "ridge"
    )

    canvas.place(x = 0, y = 0)
    canvas.create_text(
        272.0,
        41.0,
        anchor="nw",
        text="Home\n",
        fill="#D9D9D9",
        font=("Inter Bold", 40 * -1)
    )

    # Menu button
    button_image_1 = PhotoImage(
        file=relative_to_assets("button_1.png"))
    button_1 = Button(
        image=button_image_1,
        borderwidth=0,
        highlightthickness=0,
        command=lambda: [window.destroy(), get_menu()],
        relief="flat"
    )
    button_1.place(
        x=0.0,
        y=130.0,
        width=152.0,
        height=46.0
    )

    # Maintenance button
    button_image_2 = PhotoImage(
        file=relative_to_assets("maintenance.png"))
    button_2 = Button(
        image=button_image_2,
        borderwidth=0,
        highlightthickness=0,
        command=lambda: [window.destroy(), get_maintenance()],
        relief="flat"
    )
    button_2.place(
        x=0.0,
        y=205.0,
        width=152.0,
        height=46.0
    )

    # Usage button
    button_image_3 = PhotoImage(
        file=relative_to_assets("button_3.png"))
    button_3 = Button(
        image=button_image_3,
        borderwidth=0,
        highlightthickness=0,
        command=lambda: [window.destroy(), get_usage()],
        relief="flat"
    )
    button_3.place(
        x=0.0,
        y=280.0,
        width=152.0,
        height=46.0
    )

    res=[]
    driver(res)
    last30days = round(sum(i[1] for i in res[:-30:-1]),2)
    avg30days = round(last30days / 30,2)
    expected = round(sum(i[1] for i in res) / len(res),2)
    var = StringVar()
    label = Message(window, textvariable=var, aspect=1000,justify="center", padx = 20, pady = 20)
    var.set("Total usage for the last 30 days: {}\nAverageUsage per day: {}\nExpectedUsage in the next 30 days: {}".format(last30days,avg30days,expected))
    label.pack()
    label.place(x=400,y=200)

    var2 = StringVar()
    label2 = Message(window, textvariable=var2, aspect=1000,justify="center", padx = 20, pady = 20)
    var2.set("Current temperature outside: {}".format(temp()))
    label2.pack()
    label2.place(x=400,y=300)

    window.resizable(False, False)
    window.mainloop()
