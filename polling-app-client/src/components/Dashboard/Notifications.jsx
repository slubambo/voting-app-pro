import React, { Component } from 'react';
import './Notifications.css';
import { ACCESS_TOKEN } from '../../constants';

class Notifications extends Component {
  constructor(props) {
    super(props);
    this.state = {
      notifications: [],
      loading: true
    };
  }

  componentDidMount() {
    const token = localStorage.getItem(ACCESS_TOKEN);

    fetch('/api/notifications', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => res.json())
      .then(data => {
        this.setState({
          notifications: data.slice(0, 2),
          loading: false
        });
      })
      .catch(() => {
        this.setState({ notifications: [], loading: false });
      });
  }

  formatDate = (isoString) => {
    const date = new Date(isoString);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  }

  render() {
    const { notifications, loading } = this.state;

    return (
      <div className="notifications-box">
        <h3>알림 요약</h3>
        {loading ? (
          <p>불러오는 중...</p>
        ) : notifications.length === 0 ? (
          <p>새 알림이 없습니다.</p>
        ) : (
          <ul className="notification-list">
            {notifications.map(n => (
              <li key={n.id}>
                <div>{this.formatDate(n.created_at)}</div>
                <div>{n.message}</div>
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  }
}

export default Notifications;
