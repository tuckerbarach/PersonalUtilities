from __future__ import print_function
import datetime
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request

# If modifying these scopes, delete the file token.pickle.
SCOPES = ['https://www.googleapis.com/auth/calendar']


def main():
    """Shows basic usage of the Google Calendar API.
    Prints the start and name of the next 10 events on the user's calendar.
    """

    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('token.pickle'):
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token)
    # If there are no (valid) credentials available, let the user log in.
    print("Here is the scope: " + os.getcwd())
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'res/data/credentials.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)

    service = build('calendar', 'v3', credentials=creds)

    # Call the Calendar API
    now = datetime.datetime.utcnow().isoformat() + 'Z'  # 'Z' indicates UTC time
    #print('Getting the upcoming 10 events')
    events_result = service.events().list(calendarId='primary', timeMin=now,
                                          maxResults=10, singleEvents=True,
                                          orderBy='startTime').execute()
    events = events_result.get('items', [])
    items = []

    if not events:
        print('No upcoming events found.')
    for event in events:
        start = event['start'].get('dateTime', event['start'].get('date'))
        # print(start, event['summary'])
        start_time = event['start']['dateTime']
        end_time = event['end']['dateTime']  # [0:start_time.find("T")]
        times = [make_time_readable(start_time), make_time_readable(end_time)]
        items.append([times[0], times[1], event['summary']])  # start, end, title (listing)

    # write all to file:
    if len(items) > 0:
        file = open("res/data/calendar_data.txt", "w+")
        for item in items:
            for i in item:
                file.write(i + "\n")
        file.close()


def make_time_readable(time):
    # 2020-06-13T21:30:00-04:00
    year_month_day = time[0:time.find("T")]

    part_of_day = time[time.find("T") + 1: time.rfind("-")]

    return year_month_day + " at " + part_of_day + " EST"


if __name__ == '__main__':
    print("hi")
    main()
