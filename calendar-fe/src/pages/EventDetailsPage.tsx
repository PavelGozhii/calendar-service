import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { EventDTO } from '../types';
import styles from '../styles/EventDetailsPage.module.css';

const EventDetailsPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const nav = useNavigate();
    const [form, setForm] = useState<EventDTO | null>(null);
    const [error, setError] = useState<string>('');

    useEffect(() => {
        axios.get<EventDTO>(`http://localhost:8080/events/${id}`)
            .then(r => setForm(r.data))
            .catch(() => setError('Failed to load'));
    }, [id]);

    if (!form) return <div>{error || 'Loading...'}</div>;

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value } as EventDTO);
    };

    const validate = (): boolean => {
        if (new Date(form.endDateTime) < new Date(form.startDateTime)) {
            setError('End date/time must not be before start');
            return false;
        }
        return true;
    };

    const handleSave = async () => {
        if (!validate()) return;
        try {
            await axios.put(`http://localhost:8080/events/${id}`, form);
            nav('/');
        } catch {
            setError('Update failed');
        }
    };

    const handleDelete = async () => {
        await axios.delete(`http://localhost:8080/events/${id}`);
        nav('/');
    };

    return (
        <div className={styles.container}>
            {error && <div className={styles.error}>{error}</div>}
            <input
                className={styles.inputField}
                name="title"
                value={form.title}
                onChange={handleChange}
                placeholder="Title"
            />
            <textarea
                className={styles.textareaField}
                name="description"
                value={form.description}
                onChange={handleChange}
                placeholder="Description"
            />
            <input
                className={styles.inputField}
                name="location"
                value={form.location}
                onChange={handleChange}
                placeholder="Location"
            />
            <input
                className={styles.inputField}
                type="datetime-local"
                name="startDateTime"
                value={form.startDateTime}
                onChange={handleChange}
            />
            <input
                className={styles.inputField}
                type="datetime-local"
                name="endDateTime"
                value={form.endDateTime}
                onChange={handleChange}
            />
            <div className={styles.buttonGroup}>
                <button onClick={handleSave} className={`${styles.button} ${styles.saveButton}`}>Save</button>
                <button onClick={handleDelete} className={`${styles.button} ${styles.deleteButton}`}>Delete</button>
            </div>
        </div>
    );
};

export default EventDetailsPage;
