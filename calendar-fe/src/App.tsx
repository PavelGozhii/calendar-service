import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import CalendarPage from './pages/CalendarPage';
import AddEventPage from './pages/AddEventPage';
import EventDetailsPage from './pages/EventDetailsPage';
import styles from './styles/App.module.css';

function App() {
    return (
        <BrowserRouter>
            <nav className={styles.navbar}>
                <Link className={styles.navlink} to="/">Calendar</Link>
                <Link className={styles.navlink} to="/add">Add Event</Link>
            </nav>
            <Routes>
                <Route path="/" element={<CalendarPage/>}/>
                <Route path="/add" element={<AddEventPage/>}/>
                <Route path="/events/:id" element={<EventDetailsPage/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
