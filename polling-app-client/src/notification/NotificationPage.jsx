import React, { useEffect, useState } from 'react';
import { List, Card, Spin, message } from 'antd';
import axios from 'axios';

const NotificationPage = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // �鿣�� �˸� API ȣ��
    axios.get('/api/notifications')
  // ���� API ��η� ���� �ʿ�
      .then(response => {
        setNotifications(response.data);
        setLoading(false);
      })
      .catch(error => {
        message.error('�˸��� �ҷ����� �� �����߽��ϴ�.');
        setLoading(false);
      });
  }, []);

  if (loading) return <Spin tip="�ε� ��..." />;

  return (
    <div style={{ padding: 20 }}>
      <h2>�˸� ���</h2>
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
