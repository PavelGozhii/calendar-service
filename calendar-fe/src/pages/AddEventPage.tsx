import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { EventDTO } from '../types';
import styles from '../styles/AddEventPage.module.css';

const AddEventPage: React.FC = () => {
    const nav = useNavigate();
    const [form, setForm] = useState<EventDTO>({
        title: '',
        description: '',
        location: '',
        startDateTime: '',
        endDateTime: ''
    });
    const [error, setError] = useState<string>('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const validate = (): boolean => {
        if (new Date(form.endDateTime) < new Date(form.startDateTime)) {
            setError('End date/time must be same or after start date/time');
            return false;
        }
        return true;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!validate()) return;
        try {
            await axios.post('http://localhost:8080/events', form);
            nav('/');
        } catch (err: any) {
            setError(err.response?.data?.message || 'Failed to add');
        }
    };

    return (
        <form onSubmit={handleSubmit} className={styles.formContainer}>
            {error && <div className={styles.error}>{error}</div>}
            <input
                name="title"
                placeholder="Title"
                value={form.title}
                onChange={handleChange}
                required
                className={styles.inputField}
            />
            <textarea
                name="description"
                placeholder="Description"
                value={form.description}
                onChange={handleChange}
                className={styles.textareaField}
            />
            <input
                name="location"
                placeholder="Location"
                value={form.location}
                onChange={handleChange}
                className={styles.inputField}
            />
            <input
                type="datetime-local"
                name="startDateTime"
                value={form.startDateTime}
                onChange={handleChange}
                required
                className={styles.inputField}
            />
            <input
                type="datetime-local"
                name="endDateTime"
                value={form.endDateTime}
                onChange={handleChange}
                required
                className={styles.inputField}
            />
            <button type="submit" className={styles.submitButton}>Add Event</button>
        </form>
    );
};

export default AddEventPage;