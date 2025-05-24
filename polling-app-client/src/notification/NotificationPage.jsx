import React, { useEffect, useState } from 'react';
import { List, Card, Spin, message } from 'antd';
import axios from 'axios';

const NotificationPage = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 백엔드 알림 API 호출
    axios.get('/api/notifications')
  // 실제 API 경로로 수정 필요
      .then(response => {
        setNotifications(response.data);
        setLoading(false);
      })
      .catch(error => {
        message.error('알림을 불러오는 데 실패했습니다.');
        setLoading(false);
      });
  }, []);

  if (loading) return <Spin tip="로딩 중..." />;

  return (
    <div style={{ padding: 20 }}>
      <h2>알림 목록</h2>
      <List
        grid={{ gutter: 16, column: 1 }}
        dataSource={notifications}
        renderItem={item => (
          <List.Item>
            <Card title={item.title}>
              {item.message}
            </Card>
          </List.Item>
        )}
      />
    </div>
  );
};

export default NotificationPage;
