from flask import Flask, render_template, request, jsonify
import webbrowser
import subprocess
import time
import os
import atexit
import sys
#from datetime import datetime
#def open_browser():
#    webbrowser.open("http://localhost:5001/")

app = Flask(
        __name__,
        template_folder=os.path.join("templates"),
        static_folder=os.path.join("static")
        )
@app.route('/')
def home_page():
    return jsonify({
        "email": "leecash133@gmail.com",
        "phoneNumber": "+1 762 222-3156",
        "name": "Lee Cash",
        "education": "High school Diploma",
        "linkGithub": "https://github.com/LeeFCash"
    })
#
@app.route('/digital_portfolio/home_page')
def digital_portfolio_home_page():
    return render_template('index.html')
#
@app.route('/digital_portfolio/about_me_tab')
def digital_portfolio_about_me_tab():
    return render_template('aboutMeTab.html')
#
@app.route('/digital_portfolio/skills')
def digital_portfolio_skills():
    return render_template('skills.html')
#
@app.route('/digital_portfolio/contact_me')
def digital_portfolio_contact_me():
    return render_template('contactMe.html')
#
@app.route('/numberGuessingGame')
def testApp():
    return render_template('numberGuessingGame.html')
#
#webbrowser.open("http://localhost:5001/")
#
if __name__ == '__main__':
    # comment or 
    """
    this for comment
    """
    #threading.Timer(1.0, open_browser).start()# for if I want delay 
    #backend_started = start_backend()
    #
    #if backend_started:
    #    time.sleep(3)  
    #else:
    #    print("Backend unavailable")
    #time.sleep(5)
    #webbrowser.open("http://localhost:5001/")
    app.run(debug = False, use_reloader = False, port = 5001)
    #stop_backend()
