import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { EventDTO } from '../types';
import FullCalendar from '@fullcalendar/react';
import { EventClickArg } from '@fullcalendar/core';

const CalendarPage: React.FC = () => {
    const [events, setEvents] = useState<EventDTO[]>([]);
    const calendarRef = useRef<FullCalendar>(null);
    const nav = useNavigate();

    useEffect(() => {
        axios.get<EventDTO[]>('http://localhost:8080/events').then(res => {
            setEvents(res.data);
        });
    }, []);

    const handleEventClick = (clickInfo: EventClickArg) => {
        const id = clickInfo.event.extendedProps.id;
        nav(`/events/${id}`);
    };

    return (
        <FullCalendar
            ref={calendarRef}
            plugins={[dayGridPlugin, interactionPlugin]}
            initialView="dayGridMonth"
            events={events.map(e => ({ title: e.title, start: e.startDateTime, end: e.endDateTime, extendedProps: { id: e.id } }))}
            eventClick={handleEventClick}
            headerToolbar={{ left: 'prev,next today', center: 'title', right: '' }}
            height="auto"
        />
    );
};
export default CalendarPage;